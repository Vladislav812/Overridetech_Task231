let id;

async function getPrincipal() {
    await fetch("/principal")
        .then((response) => response.json())
        .then((json) => {
            id = json.id;
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

async function getNearestPostOffices() {
    let url = "/postoffices?id=" + id;
    await fetch(url)
        .then((response) => response.json())
        .then((json) => {
            let len = json.length;
            const table = document.getElementById("usertableaddressbody");
            table.innerHTML = ``;
            for (let i = 0; i < len; i++) {
                table.innerHTML += `
                    <tr>
                       <td class="users__cell w-auto">${json[i].postal_code}</td>
                       <td class="users__cell w-auto">${json[i].address_str}</td>
                    </tr>
                `;
            }
        });
}

async function showSummaryProductTable() {
    await fetch("/products?seller=" + id)
        .then((response) => response.json())
        .then((json) => {
            let len = json.length;
            const tableBody = document.getElementById("summary-product-table-body");
            tableBody.innerHTML = ``;
            for (let i = 0; i < len; i++) {
                let imgsource = json[i].image;
                tableBody.innerHTML += `
                <tbody>
                    <tr>
                        <td class="users__cell w-auto">${json[i].title}</td>
                        <td class="users__cell w-auto"><img src="${imgsource}" width="300" height="200"></td>
                        <td class="users__cell w-auto">${json[i].description}</td>
                        <td class="users__cell w-auto">${json[i].quantity}</td>
                        <td class="users__cell w-auto">${json[i].price}</td>
                        <td class="users__cell w-auto">${json[i].sellerName}</td>
                        <td class="users__cell">
                            <button class="button button--edit w-auto" onclick="editProduct('${json[i].id}')">Edit Product</button>
                        </td>
                        <td class="users__cell">
                          <button class="button button--delete w-auto" onclick="deleteProduct('${json[i].id}')">Delete Product</button>
                        </td>
                    </tr>
                </tbody>
                `;
            }
        });
}

async function createProductCard() {
    let file = document.getElementById("selectImage").files[0];
    if (typeof file == 'undefined') {
        alert('Upload an image!');
        return;
    }
    let base64String = await readImageToBase64(file);
    const productDescription = document.getElementById("enterDescription").value;
    const productQuantity = document.getElementById("enterQuantity").value;
    if (productQuantity < 0) {
    alert ("Quantity cannot be negative!");
    return;
    }
    const productPrice = document.getElementById("enterPrice").value;
    const productTitle = document.getElementById("enterTitle").value;
    let newProduct = {
        title: productTitle,
        image: base64String,
        description: productDescription,
        quantity: productQuantity,
        price: productPrice,
        sellerId: id,
    };
    await fetch("/products", {
        method: "POST",
        body: JSON.stringify(newProduct),
        headers: {
            "Content-type": "application/json; charset=UTF-8",
        },
    });
    await showSummaryProductTable();

    document.getElementById("enterDescription").value = "";
    document.getElementById("enterQuantity").value = "";
    document.getElementById("enterPrice").value = "";
    document.getElementById("enterTitle").value ="";
    document.getElementById("selectImage").value = "";
    document.getElementById("preload-image-icon").innerHTML=`<td id="preload-image-icon"><input type="file" id="selectImage" onchange="showImage(event)"/><img src="" id="uploadingImage"></td>`;

}

async function readImageToBase64(file) {
    let base_64 = await new Promise(resolve => {
        let reader = new FileReader();
        reader.onload = (e) => resolve(reader.result);
        reader.readAsDataURL(file);
    });
    return base_64;
}

function showImage() {
    const img = document.getElementById("uploadingImage");
    img.src = URL.createObjectURL(event.target.files[0]);
    img.width = "220";
    img.height = "180";
}

async function deleteProduct(productId) {
    await fetch("/products", {
        method: "DELETE",
        body: productId,
    });
    await showSummaryProductTable();
}

async function editProduct(productId) {
    localStorage.setItem('productId' , productId)
    window.open("/productpage");
}
