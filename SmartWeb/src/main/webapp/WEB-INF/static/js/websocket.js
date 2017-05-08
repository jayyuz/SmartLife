/**
 * Created by Administrator on 2016/10/5.
 */

src = "http://cdn.sockjs.org/sockjs-0.3.min.js"

var ws = null;
var url = null;
var transports = [];

function setConnected(connected) {

}

function connect() {
    if (!url) {
        return;
    }
    ws = new WebSocket(url);

    ws.onopen = function () {
        // setConnected(true);
        sentMsg("hello");
    };
    ws.onmessage = function (event) {
        if (event.data == 'refresh') {
            location.reload();
        }
    };
    ws.onclose = function (event) {
        // setConnected(false);
    };

}

function disconnect() {
    if (ws != null) {
        ws.close();
        ws = null;
    }
    setConnected(false);
}

function echo() {
    if (ws != null) {
        var message = document.getElementById('message').value;
        log('Sent: ' + message);
        ws.send(message);
    } else {
        alert('connection not established, please connect.');

    }
}

function sentMsg(message) {
    if (ws != null) {
        if (ws.readyState == 1) {
            ws.send(message);
        }
    } else {
        $.alert('connection not established, please connect.');
        location.reload();
    }
}

function updateUrl(urlPath) {
    if (urlPath.indexOf('sockjs') != -1) {
        url = urlPath;
        // document.getElementById('sockJsTransportSelect').style.visibility = 'visible';
    } else {
        if (window.location.protocol == 'http:') {
            url = 'ws://' + window.location.host + urlPath;
        } else {
            url = 'wss://' + window.location.host + urlPath;
        }
        // document.getElementById('sockJsTransportSelect').style.visibility = 'hidden';
    }
}

function updateTransport(transport) {
    alert(transport);
    transports = (transport == 'all') ? [] : [transport];
}

function log(message) {
    // var console = document.getElementById('console');
    // var p = document.createElement('p');
    // p.style.wordWrap = 'break-word';
    // p.appendChild(document.createTextNode(message));
    // console.appendChild(p);
    // while (console.childNodes.length > 25) {
    //     console.removeChild(console.firstChild);
    // }
    // console.scrollTop = console.scrollHeight;
}