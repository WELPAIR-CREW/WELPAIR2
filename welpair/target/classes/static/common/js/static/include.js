function includeHTML() {
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

window.onload = includeHTML;