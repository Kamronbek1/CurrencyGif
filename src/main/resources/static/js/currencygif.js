const general_url = './api/';

function loadResultGif() {
    let code = $("#codes_select").val();
    console.log(code);
    $.ajax({
        url: general_url + 'gif/' + code,
        method: 'GET',
        dataType: "json",
        complete: function (data) {
            let content = JSON.parse(data.responseText);
            let img = document.createElement("img");
            let gifName = document.createElement("p");
            gifName.textContent = content.data.title;
            let gifKey = document.createElement("p");
            gifKey.textContent = content.compareResult;
            img.src = content.data.images.original.url;
            let out = document.querySelector("#out");
            out.innerHTML = '';
            out.insertAdjacentElement("afterbegin", img);
            out.insertAdjacentElement("afterbegin", gifName);
            if (gifKey.textContent === 'no access') {
                let description = document.createElement("p");
                description.textContent = 'Changing the API `base` currency is available for Developer, ' +
                    'Enterprise and Unlimited plan clients. Please upgrade, or contact ' +
                    'support@openexchangerates.org with any questions.';
                out.insertAdjacentElement("afterbegin", description);
            }
            out.insertAdjacentElement("afterbegin", gifKey);
        }
    })
}

function loadForSelect() {
    $.ajax({
        url: general_url + 'codes/all',
        method: 'GET',
        complete: function (data) {
            let codesList = JSON.parse(data.responseText);
            let select = document.querySelector("#codes_select");
            select.innerHTML = '';
            for (let i = 0; i < codesList.length; i++) {
                let option = document.createElement("option");
                option.value = codesList[i];
                option.text = codesList[i];
                select.insertAdjacentElement("beforeend", option);
            }
        }
    })
}
