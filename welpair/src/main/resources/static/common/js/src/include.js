export function includeHTML() {
    document.querySelectorAll('.include').forEach(el => {
        const target = el.dataset.includeHtml;

        if (target) {
            let xhttp = new XMLHttpRequest();

            xhttp.onreadystatechange = function () {
                if (this.readyState !== XMLHttpRequest.DONE) return;

                if (this.status === 200) {

                    let frag = document.createDocumentFragment();
                    let range = document.createRange();
                    el.innerHTML = this.responseText;
                    range.selectNodeContents(el);
                    frag.appendChild(range.extractContents());
                    el.replaceWith(frag);

                    if (target.includes("header")) {
                        var searchButton = document.getElementById('search-button');
                        if (searchButton) {
                            searchButton.addEventListener('click', submitSearchForm);
                        }
                    }
                } else if (this.status === 404) {

                    console.log('Page not found');
                }
            }

            xhttp.open('get', target, true);
            xhttp.send();

            return;
        }
    });
}

function submitSearchForm(event) {
    event.preventDefault();
    console.log("옴옴옴??")

    let searchTerm = document.getElementsByName("title")[0].value;
    let searchURL = "search/search?title=" + searchTerm;

    window.location.href = searchURL;
}

window.onload = includeHTML;