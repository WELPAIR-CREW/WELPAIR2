export const call = (url, method, request) => {
    let options = {
        headers: {
            'Content-Type': 'application/json',
        },
        method: method
    };

    if (request) {
        options.body = JSON.stringify(request);
    }

    return fetch(url, options)
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            return Promise.reject(response);
        })
        .catch(error => {
            console.log(error);
            return Promise.reject(error);
        })
}
