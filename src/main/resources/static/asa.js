function sendDelete(url) {
    var xhttp = new XMLHttpRequest();
    xhttp.open("DELETE", url, true);
    xhttp.onload = function () {
        let responseURL = xhttp.responseURL;
        console.log("Redirecting to:", responseURL);
        window.location.replace(responseURL);
    };
    xhttp.send();
}

function sendEdit(url) {
    var xhttp = new XMLHttpRequest();
    xhttp.open("GET", url, true);
    xhttp.onload = function () {
        let responseURL = xhttp.responseURL;
        console.log("Redirecting to:", responseURL);
        window.location.replace(responseURL);
    };
    xhttp.send();
}