var eventSource = new EventSource("/vertx/greeting/Quarkus/streaming");

const onmessage = (event) => {
    var container = document.getElementById("container");
    var paragraph = document.createElement("p");
    paragraph.innerHTML = event.data;
    container.appendChild(paragraph);
}

eventSource.onmessage = onmessage;
