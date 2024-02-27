function sendData() {
    var data = { name: 'John', age: 30 }; // Data to be sent

    fetch('https://localhost:8080/testjs', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (response.ok) {
            console.log('Data sent successfully!');
        } else {
            console.error('Error sending data:', response.statusText);
        }
    })
    .catch(error => {
        console.error('Error sending data:', error);
    });
}