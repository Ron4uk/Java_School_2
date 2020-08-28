function dukeMessageListener(message) {
    console.log(message);
    facesmessage.severity = 'info';
    PF('broadcastGrowl').show(message);
}