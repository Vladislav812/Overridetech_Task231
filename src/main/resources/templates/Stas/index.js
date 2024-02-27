const _button = document.createElement("button");
_button.data = "hi";
_button.innerHTML = 'click me';
_button.onclick = function () {
    alert("hello, world");
}

document.body.appendChild(_button);


function tableCreate(rows, cols) {
    //body reference
    var body = document.getElementsByTagName("body")[0];

    // create elements <table> and a <tbody>
    var tbl = document.createElement("table");
    var tblBody = document.createElement("tbody");

    // cells creation
    for (var j = 0; j < rows; j++) {
        // table row creation
        var row = document.createElement("tr");

        for (var i = 0; i < cols; i++) {
            // create element <td> and text node
            //Make text node the contents of <td> element
            // put <td> at end of the table row
            var cell = document.createElement("td");
            var cellText = document.createTextNode("cell is row " + j + ", column " + i);

            cell.id = "someId";

            cell.style.width = '50px';
            cell.style.height = '50px';

            cell.appendChild(cellText);
            row.appendChild(cell);
        }

        //row added to end of table body
        tblBody.appendChild(row);
    }

    // append the <tbody> inside the <table>
    tbl.appendChild(tblBody);
    // put <table> in the <body>
    body.appendChild(tbl);
    // tbl border attribute to
    tbl.setAttribute("border", "2");
}



const fillData = () => {
    const request = new Request('http://localhost:8080/hello', {
        method: 'GET',
        // headers: new Headers({
        //     "Authorization": token,
        //     "Content-Type": "application/json"
        // }),
        // body: payload
    });


    // Using it
    fetch(request)
        .then((response) => {
            if (!response.ok) { // Don't forget this part!
                throw new Error(`HTTP error ${response.status}`);
            }
            return response.json();
        })
        .then((responseData) => {
            console.log(responseData);

            tableCreate(responseData.length, 10);

            // Пример того, как доставать данные из объекта
            const title = responseData[0].currentRoles[0].title;

            // Пример того, как просовывать данные в HTML разметку, связь по id
            const elements = document.querySelectorAll('[id=someId]');

            for (let i = 0; i < elements.length; i++) {
                elements[i].innerHTML = title;
            }




            // document.getEle getElementById('someId').innerText = title;
        });
}


window.addEventListener('load', () => {

    // TODO Отрисовать таблицу здесь

    fillData();
})


document.getElementById("myButton").addEventListener('click', () => {

// Building the request, which you can then pass around


});


