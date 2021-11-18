
function DataBaseRef(path) {
    var path = null;
    var database = null;
    this.path = '';
};

DataBaseRef.prototype.child = function(path) {
    this.path = this.path + '/' + path;
    return this;
};

DataBaseRef.prototype.on = function(destination, callback) {

    this.destination = destination;
    var url = '/topic' + this.path + '/' + destination;

    console.log('🐳 Subscribing to ' + url)
    this.database.client.subscribe(url, function (message) {
        callback(message);
    });

    if (destination == 'list') {
        // Perform the loading
        url = '/db' + this.path + '/' + destination;

        console.log('📣 Initiating data [' + url + ']');
        this.database.client.send(url, {}, {});
    }
};

DataBaseRef.prototype.add = function(object) {

    var url = '/db' +  this.path + '/add';
    console.log('😀 Saving [' + object + ']: ' + url);
    this.database.client.send(url, {}, object);
}

DataBaseRef.prototype.remove = function(object) {

    var url = '/db' +  this.path + '/remove';
    console.log('😀 Removing [' + object + ']: ' + url);
    this.database.client.send(url, {}, object);
}


var Database = function(url) {

    var client = null;
    var endpoint = null;
    var connected = false;
    this.endpoint = url;

    return this;
};

Database.prototype.ref = function() {
    var ref = new DataBaseRef();
    ref.database = this;
    return ref;
};

Database.prototype.connect = function(callback) {

    if (!this.endpoint) {
        return
    }

    var socket = new SockJS(this.endpoint);
    this.client = Stomp.over(socket);

    this.client.connect({}, function (session) {

        this.connected = true;
        if (callback) {
            console.log('📞');
            callback(this.connected);
        }
    });
};

Database.prototype.disconnect = function(callback) {
    if (this.client !== null) {
        this.client.disconnect();
    }
};

// Use the database
var database = null;
var auditEvents = null;

function addObservers() {

    auditEvents = database.ref().child('auditEvents');

    auditEvents.on('add', function(message) {
        console.log('📩 ADD: ' + message);
        var json = JSON.parse(message.body);
        addAuditEvent(json);
    });

    auditEvents.on('list', function(message) {
        console.log('📩 LIST: ' + message);
        var json = JSON.parse(message.body);
        for (var key in json) {
            addAuditEvent(json[key]);
        }
    });

    auditEvents.on('remove', function(message) {
        console.log('📩 REMOVE: ' + message);
        var json = JSON.parse(message.body);
        var selector = 'table#auditEvents-table tr#' + json.id;
        $(selector).remove();
    });

    $("#send").click(function() {
        var auditEvent = JSON.stringify(
            {
                'eventCategory': $("#eventCategory").val(),
                'eventType':'walk',
                'principal':'hello',
                'hostname':'Natalie',
                'platformVersion':'1.0',
                'eventData':'yaaaas',
                'timestamp': new Date()
            });
        auditEvents.add(auditEvent);
    });

}

function deleteAuditEvent(id) {

    var auditEvent = JSON.stringify({'id': id});
    auditEvents.remove(auditEvent);
}

function addAuditEvent(auditEvent) {
    var button = "<button onclick=\"deleteAuditEvent('" + auditEvent.id + "');\" class=\"btn btn-danger btn-delete\" type=\"submit\">Delete</button>";
    $("#auditEvent-rows").append("<tr id=\"" + auditEvent.id + "\"><td>" + auditEvent.id + "</td><td>" + auditEvent.eventCategory + "</td><td>" + button + "</td></tr>");
}

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#auditEvents-table").show();
    } else {
        $("#auditEvents-table").hide();
    }
    $("#auditEvent-rows").html("");
}


$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function() {

    });
    $("#disconnect").click(function() {

    });

    database = new Database('/example-ws');
    database.connect(function(connected) {
        if (connected) {
            addObservers();
        } else {
            console.log('Unable to connect!');
        }
    });


});


