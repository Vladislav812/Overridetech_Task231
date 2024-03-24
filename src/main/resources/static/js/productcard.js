async function getProductData() {
    const productIdToEdit = localStorage.getItem('productId');
    if (productIdToEdit === null || productIdToEdit === undefined) {
        alert("No Data to Show!");
        window.close();
    }
    const productCard = document.getElementById("product-card-table-body");
    productCard.innerHTML = ``;

    await fetch("/product/" + productIdToEdit)
        .then((response) => response.json())
        .then((json) => {
            let imgsource = json.image;
            productCard.innerHTML += `
            <tbody>
                <tr>
                    <td class="users__cell w-auto">
                        <div>
                            <label for="enterTitle"></label>
                            <input type="text" id="enterTitle" value="${json.title}"/>
                        </div>
                    </td>
                    <td id="preload-image-icon"><input type="file" id="selectImage" onchange="showImage(event)" /><img src="${json.image}" id="uploadingImage" width="220" height="180" /></td>
                    <td class="users__cell w-auto">
                        <div class="description-text">
                            <label for="enterDescription"></label>
                            <input class="users__cell w-auto" type="text" role="textbox" id="enterDescription" value="${json.description}"/>
                        </div>
                    </td>
                    <td class="users__cell w-auto">
                        <div>
                            <label for="enterQuantity"></label>
                            <input type="number" id="enterQuantity" value="${json.quantity}"/>
                        </div>
                    </td>
                    <td class="users__cell w-auto">
                        <div>
                            <label for="enterPrice"></label>
                            <input type="number" id="enterPrice" value="${json.price}" />
                        </div>
                    </td>

                    <td class="users__cell w-auto">${json.sellerName}</td>
                </tr>
            </tbody>
            `;
        });
}
getProductData();

function showImage() {
    const img = document.getElementById("uploadingImage");
    img.src = URL.createObjectURL(event.target.files[0]);
    img.width = "220";
    img.height = "180";
}


async function patchProduct() {
    let file = document.getElementById("selectImage").files[0];
    let base64String;
    if (typeof file !== 'undefined') {
        base64String = await readImageToBase64(file);
    } else base64String = document.getElementById("uploadingImage").src;

    const productTitle = document.getElementById("enterTitle").value;
    const productDescription = document.getElementById("enterDescription").value;
    const productQuantity = document.getElementById("enterQuantity").value;
    if (productQuantity < 0) {
        alert ("Quantity cannot be negative!");
        return;
        }
    const productPrice = document.getElementById("enterPrice").value;

    let newProduct = {
        id: localStorage.getItem('productId'),
        title: productTitle,
        image: base64String,
        description: productDescription,
        quantity: productQuantity,
        price: productPrice,
    };
    await fetch("/products", {
        method: "PATCH",
        body: JSON.stringify(newProduct),
        headers: {
            "Content-type": "application/json; charset=UTF-8",
        },
    });
    localStorage.setItem('productId', null);
    window.close();
}


async function readImageToBase64(file) {
    let base_64 = await new Promise(resolve => {
        let reader = new FileReader();
        reader.onload = (e) => resolve(reader.result);
        reader.readAsDataURL(file);
    });
    return base_64;
}