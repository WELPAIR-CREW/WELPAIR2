
document.addEventListener('DOMContentLoaded', function() {
    var searchButton = document.getElementById('search-button');
    if (searchButton) {
        searchButton.addEventListener('click', submitSearchForm);
    }
});

function submitSearchForm() {
    function submitSearchForm() {
        console.log("왔어")
        var searchTerm = document.getElementsByName("prodSearchInput")[0].value;
        var searchURL = "/consumer/search/search?term=" + encodeURIComponent(searchTerm);

        // Set the search URL as the form action
        document.getElementById("search-form").action = searchURL;

        // Submit the form synchronously
        document.getElementById("search-form").submit();

        // Navigate to the desired page after querying
        window.location.href = "/consumer/search/search";
    }
}

