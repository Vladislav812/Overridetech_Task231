async function showSummaryProductTable() {
    await fetch("/products/list")
        .then((response) => response.json())
        .then((json) => {
            let len = json.length;
            const tableBody = document.getElementById("summary-product-table-body");
            tableBody.innerHTML = ``;
            for (let i = 0; i < len; i++) {

                tableBody.innerHTML += `
                <tbody>
                    <tr>
                        <td class="users__cell w-auto" id="product-${json[i].id}-image"><img src="${json[i].image}" width="220" height="180"></td>
                        <td class="users__cell w-auto" id="product-${json[i].id}-title">${json[i].title}</td>
                        <td class="users__cell w-auto" id="product-${json[i].id}-description">${json[i].description}</td>
                        <td class="users__cell w-auto" id="product-${json[i].id}-quantity">${json[i].quantity}</td>
                        <td class="users__cell w-auto" id="product-${json[i].id}-price">${json[i].price}</td>
                        <td class="users__cell w-auto" id="product-${json[i].id}-seller-name">${json[i].sellerName}</td>
                        <td class="users__cell">
                            <button class="button button--edit w-auto" onclick="addToCart(${json[i].id})">Add to Shopping Cart</button>
                        </td>
                    </tr>
                </tbody>
                `;
            }
        });
}
showSummaryProductTable();

let productsInCart = new Map();
function addToCart(productId){
    let prodQuantity = document.getElementById(`product-${productId}-quantity`).textContent;

    if (prodQuantity > 0) {
        prodQuantity--;
        document.getElementById(`product-${productId}-quantity`).innerText=prodQuantity;

        if (productsInCart.has(productId)) {
            let value = productsInCart.get(productId);
            productsInCart.set(productId, ++value);
        } else {
            productsInCart.set(productId, 1);
        }

    } else alert("There aren't enough product in stock!");
}




async function commitOrder() {
    if (productsInCart.size == 0) alert("YOUR CART IS EMPTY!");
    else {
        await fetch("/commitorder", {
            method: "POST",
            headers: {
                "Content-type": "application/json; charset=UTF-8",
            },
            body: JSON.stringify(Object.fromEntries(productsInCart)),
        })
            .then((response) => {
                if (response.status == 200) alert("Commited successfully!");
                else {
                    alert("Something went wrong!");
                }
            })
            .then((e) => showSummaryProductTable());
    }
}

