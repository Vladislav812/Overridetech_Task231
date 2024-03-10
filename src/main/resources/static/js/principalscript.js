let id;

function getPrincipal() {
    fetch("/principal")
        .then((response) => response.json())
        .then((json) => {
            id=json.id;
            const table = document.getElementById("principaltablebody");
            table.innerHTML += `
                <tr>
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


async function testRestConsume() {
    let url = "/restconsume?id="+id;
    await fetch(url)
        .then(response => response.json())
        .then(json => {
            let len = json.length;
            const table = document.getElementById("usertableaddressbody");
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