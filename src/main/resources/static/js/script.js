const rolesMap = new Map();
async function createRoles() {
    await fetch("/roles")
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
    await fetch("/users")
        .then((response) => response.json())
        .then((json) => {
            let len = json.length;
            clearTableBody("summaryuserstable");
            for (let i = 0; i < len; i++) {
                const table = document.getElementById("summaryuserstable");
                table.innerHTML += `
                    <tr>
                        <td class="users__cell w-auto">${json[i].id}</td>
                        <td class="users__cell w-auto">${json[i].name}</td>
                        <td class="users__cell w-auto">${json[i].age}</td>
                        <td class="users__cell w-auto">${json[i].email}</td>
                        <td class="users__cell w-auto">${json[i].address}</td>
                        <td class="users__cell w-auto">${json[i].rolesString}</td>
                        <td class="users__cell">
                           <button type="button" class="btn btn-primary button button--edit" data-toggle="modal" data-target="#edit${json[i].id}"><a>Edit</a></button>
                           <div class="modal fade" id="edit${json[i].id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                               <div class="modal-dialog" role="document">
                                   <div class="modal-content">
                                       <div class="modal-header">
                                           <h5 class="modal-title" id="exampleModalLabel">Edit ${json[i].name}</h5>
                                           <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                               <span aria-hidden="true">&times;</span>
                                           </button>
                                       </div>
                                       <div class="modal-body">
                                           <form id="addUser${json[i].id}">
                                               <div class="form-group">
                                                   <label for="inputFullName">Full Name</label>
                                                   <input type="text" class="form-control" id="inputFullName${json[i].id}" aria-describedby="nameHelp" placeholder="Enter Full Name" value="${json[i].name}" />
                                               </div>
                                               <div class="form-group">
                                                   <label for="inputAge">Age</label>
                                                   <input type="number" class="form-control" id="inputAge${json[i].id}" placeholder="Age" value="${json[i].age}"/>
                                               </div>
                                               <div class="form-group">
                                                   <label for="inputPassword">Password</label>
                                                   <input type="password" class="form-control" id="inputPassword${json[i].id}" placeholder="Password" autocomplete="on"/>
                                               </div>
                                               <div class="form-group">
                                                   <label for="inputEmail">Email</label>
                                                   <input type="email" class="form-control" id="inputEmail${json[i].id}" aria-describedby="emailHelp" placeholder="Enter email" value="${json[i].email}" />
                                               </div>
                                               <div class="form-group">
                                                   <label for="inputAddress">Address</label>
                                                   <input type="address" class="form-control" id="inputAddress${json[i].id}" aria-describedby="addressHelp" placeholder="Enter address" value="${json[i].address}" />
                                               </div>
                                               <div class="form-group">
                                                   <label for="formControlSelector">Select role or roles</label>
                                                   <select multiple class="form-control" id="formControlSelector${json[i].id}">
                                                   </select>
                                               </div>
                                           </form>
                                       </div>
                                       <div class="modal-footer">
                                           <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                           <button type="button" class="btn btn-primary" onclick="editUser('${json[i].id}')" data-dismiss="modal">Save changes</button>
                                       </div>
                                   </div>
                               </div>
                           </div>
                        </td>
                        <td class="users__cell ">
                           <button class="button button--delete" onclick="deleteUser('${json[i].id}')">Delete</button>
                        </td>
                    </tr>
                `;
                let len = rolesMap.length;
                for (const key of rolesMap.keys()) {
                    const opt = document.createElement("option");
                    const optext = document.createTextNode(key);
                    opt.appendChild(optext);
                    document.getElementById("formControlSelector" + json[i].id).appendChild(opt);
                }
            }
        });
}
getData();

async function createUser() {
    const inputFullName = document.getElementById("inputFullName").value;
    const inputAge = document.getElementById("inputAge").value;
    const inputPassword = document.getElementById("inputPassword").value;
    const inputEmail = document.getElementById("inputEmail").value;
    const inputAddress = document.getElementById("inputAddress").value;
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
        address: inputAddress,
        currentRoles: rolArr,
    };

    await fetch("/users", {
        method: "POST",
        body: JSON.stringify(newUser),
        headers: {
            "Content-type": "application/json; charset=UTF-8",
        },
    });
    await getData();
}

async function deleteUser(uid) {
    console.log(uid);
    let del = { id: uid };
    await fetch("/users", {
        method: "DELETE",
        body: JSON.stringify(del),
        headers: {
            "Content-type": "application/json; charset=UTF-8",
        },
    });
    getData();
}

function clearTableBody(bodyName) {
    var table = document.getElementById(bodyName);
    table.innerHTML = "";
}

let id;
function getPrincipal() {
    fetch("/principal")
        .then((response) => response.json())
        .then((json) => {
            id=json.id;
            const table = document.getElementById("principaltablebody");
            table.innerHTML = "";
            table.innerHTML += `
                     <tr class="gx-5">
                         <td class="users__cell w-auto">${json.id}</td>
                         <td class="users__cell w-auto">${json.name}</td>
                         <td class="users__cell w-auto">${json.age}</td>
                         <td class="users__cell w-auto">${json.email}</td>
                         <td class="users__cell w-auto">${json.address}</td>
                         <td class="users__cell w-auto">${json.rolesString}</td>
                     </tr>
                `;

            document.getElementById("navbarcaption").innerHTML = `USER <b>   ${json.name}   </b> with email: <b>   ${json.email}   </b> with roles:   <b>${json.rolesString}   </b>`;
        });
}
getPrincipal();

async function editUser(userid) {
    const inputFullName = document.getElementById("inputFullName" + userid).value;
    const inputAge = document.getElementById("inputAge" + userid).value;
    const inputPassword = document.getElementById("inputPassword" + userid).value;
    const inputEmail = document.getElementById("inputEmail" + userid).value;
    const inputAddress = document.getElementById("inputAddress" + userid).value;
    let formControlSelector = Array.from(document.getElementById("formControlSelector" + userid).options)
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
        id: userid,
        name: inputFullName,
        age: inputAge,
        password: inputPassword === "" ? null : inputPassword,
        email: inputEmail,
        address: inputAddress,
        currentRoles: rolArr.length === 0 ? null : rolArr
    };

    await fetch("/users", {
        method: "PATCH",
        body: JSON.stringify(newUser),
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    });
    await getData();
    getPrincipal();
}

async function getNearestPostOffices() {
    let url = "/postoffices?id="+id;
    await fetch(url)
        .then(response => response.json())
        .then(json => {
            let len = json.length;
            const table = document.getElementById("usertableaddressbody");
            table.innerHTML = ``;
            for (let i = 0; i < len; i++){
                table.innerHTML += `
                    <tr>
                       <td class="users__cell w-auto">${json[i].postal_code}</td>
                       <td class="users__cell w-auto">${json[i].address_str}</td>
                    </tr>
                `;
            }
        });
}

