const rolesMap = new Map();
function createRoles() {
    fetch("http://localhost:8080/getroles")
        .then((response) => response.json())
        .then((json) => {
            let len = json.length;
            for (let i = 0; i < len; i++) {
                const opt = document.createElement("option");
                const optext = document.createTextNode(json[i].title.substring(5).toUpperCase());
                opt.appendChild(optext);
                document.getElementById("formControlSelector").appendChild(opt);
                rolesMap.set(json[i].title.substring(5).toUpperCase(), json[i].id);
            }
        });
}
createRoles();

async function getData() {
    console.log("getting users data");
    await fetch("http://localhost:8080/sayhello")
        .then((response) => response.json())
        .then((json) => {
            let len = json.length;
            clearTableBody("summaryuserstable");
            for (let i = 0; i < len; i++) {
                const table = document.getElementById("summaryuserstable");
                table.innerHTML += `
                    <tr>
                        <td class="users__cell col">${json[i].name}</td>
                        <td class="users__cell col">${json[i].age}</td>
                        <td class="users__cell col">${json[i].email}</td>
                        <td class="users__cell col">${json[i].password}</td>
                        <td class="users__cell col">${json[i].rolesString}</td>
                        <td class="users__cell">
                           <!-- Button trigger modal -->
                           <button type="button" class="btn btn-primary button button--edit" data-toggle="modal" data-target="#exampleModal">
                           <a class="link">Edit</a>
                           </button>
                           <!-- Modal -->
                           <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
                              aria-labelledby="exampleModalLabel" aria-hidden="true">
                              <div class="modal-dialog" role="document">
                                 <div class="modal-content">
                                    <div class="modal-header">
                                       <h5 class="modal-title" id="exampleModalLabel">Edit ${json[i].name}</h5>
                                       <button type="button" class="close" data-dismiss="modal"
                                          aria-label="Close">
                                       <span aria-hidden="true">&times;</span>
                                       </button>
                                    </div>
                                    <div class="modal-body">

                                    <form id="addUser">
                                        <div class="form-group">
                                            <label for="inputFullName">Full Name</label>
                                            <input type="text" class="form-control" id="inputFullName" aria-describedby="nameHelp" placeholder="Enter Full Name" />
                                        </div>
                                        <div class="form-group">
                                            <label for="inputAge">Age</label>
                                            <input type="number" class="form-control" id="inputAge" placeholder="Age" />
                                        </div>
                                        <div class="form-group">
                                            <label for="inputPassword">Password</label>
                                            <input type="password" class="form-control" id="inputPassword" placeholder="Password" />
                                        </div>
                                        <div class="form-group">
                                            <label for="inputEmail">Email address</label>
                                            <input type="email" class="form-control" id="inputEmail" aria-describedby="emailHelp" placeholder="Enter email" />
                                        </div>
                                        <div class="form-group">
                                            <label for="formControlSelector">Example multiple select</label>
                                            <select multiple class="form-control" id="formControlSelector"> </select>
                                        </div>
                                        <button type="submit" class="btn btn-primary" onclick="createUser()">Create User</button>
                                    </form>


                                    </div>
                                    <div class="modal-footer">
                                       <button type="button" class="btn btn-secondary"
                                          data-dismiss="modal">Close
                                       </button>
                                       <button type="button" class="btn btn-primary">Save changes
                                       </button>
                                    </div>
                                 </div>
                              </div>
                           </div>
                        </td>
                        <td class="users__cell ">
                              <button class="button button--delete" onclick="deleteUser('${json[i].name}')" >Delete</button>
                        </td>
                    </tr>
                `;
            }
        });
}
getData();

async function createUser() {
    const inputFullName = document.getElementById("inputFullName").value;
    const inputAge = document.getElementById("inputAge").value;
    const inputPassword = document.getElementById("inputPassword").value;
    const inputEmail = document.getElementById("inputEmail").value;
    let formControlSelector = Array.from(document.getElementById("formControlSelector").options)
        .filter((option) => option.selected)
        .map((option) => rolesMap.get(option.value));
    let len = formControlSelector.length;
    const rolArr = [];
    for (let i = 0; i < len; i++) {
        const o = {
            id: formControlSelector[i],
        };
        rolArr[i] = o;
    }

    let newUser = {
        name: inputFullName,
        age: inputAge,
        password: inputPassword,
        email: inputEmail,
        currentRoles: rolArr,
    };
    await fetch("http://localhost:8080/createuser", {
        method: "POST",
        body: JSON.stringify(newUser),
        headers: {
            "Content-type": "application/json; charset=UTF-8",
        },
    })
        .then((response) => response.json())
        .then((json) => {
            console.log(JSON.parse(json));
        });
    await getData();
}

async function deleteUser(name) {
    await fetch("http://localhost:8080/deleteuser", {
        method: "DELETE",
        body: name,
    });
    getData();
}

function clearTableBody(bodyName) {
    var table = document.getElementById(bodyName);
    table.innerHTML = "";
}

function getPrincipal() {
    fetch("http://localhost:8080/principal")
        .then((response) => response.json())
        .then((json) => {
            const table = document.getElementById("principaltablebody");
            table.innerHTML += `
                     <tr>
                         <td class="users__cell col">${json.name}</td>
                         <td class="users__cell col">${json.age}</td>
                         <td class="users__cell col">${json.email}</td>
                         <td class="users__cell col">${json.password}</td>
                         <td class="users__cell col">${json.rolesString}</td>
                     </tr>
                `;

            document.getElementById("navbarcaption").innerHTML = `USER <b>   ${json.name}   </b> with email: <b>   ${json.email}   </b> with roles:   <b>${json.rolesString}   </b>`;
        });
}
getPrincipal();
