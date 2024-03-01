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
