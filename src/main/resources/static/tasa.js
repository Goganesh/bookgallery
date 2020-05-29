/*All*/
function url() {
    let currentUrl = window.location.pathname;
    let url = '/api' + currentUrl;
    return url;
}

function sendEdit(url) {
    let xhttp = new XMLHttpRequest();
    xhttp.open("GET", url, true);
    xhttp.onload = function () {
        let responseURL = xhttp.responseURL;
        console.log("Redirecting to:", responseURL);
        window.location.replace(responseURL);
    };
    xhttp.send();
}
/*---*/

/*Books*/
function fillBooksData(){
    $.get(url()).done(function (data) {
        data.forEach(function (object) {
            $('tbody').append(`
                <tr>
                    <td>${object.id}</td>
                    <td>${object.name}</td>
                    <td>${object.author.name}</td>
                    <td id="${object.id}"></td>
                    <td>
                        <button onclick="sendEdit('/books/${object.id}')">Edit</button>
                    </td>
                    <td>
                        <button onclick="sendBooksDelete('/books/${object.id}')">Delete</button>
                    </td>
                </tr>
            `)
            $.each(object.genres, function(index, value){
                $('#' + object.id).append(value.name + ' ');
            });
        });
    })
}

function sendBooksDelete(url) {
    $.ajax({
        url: url,
        type: 'DELETE',
        success: function(data){
            $('tbody').empty();
            fillBooksData();
        }
    })
}
/*---*/

/*Genres*/
function fillGenresData(){
    $.get(url()).done(function (data) {
        data.forEach(function (object) {
            $('tbody').append(`
                <tr>
                    <td>${object.id}</td>
                    <td>${object.name}</td>
                    <td>
                        <button onclick="sendEdit('/genres/${object.id}')">Edit</button>
                    </td>
                    <td>
                        <button onclick="sendGenresDelete('/genres/${object.id}')">Delete</button>
                    </td>
                </tr>
            `)
        });
    })
}

function sendGenresDelete(url) {
    $.ajax({
        url: url,
        type: 'DELETE',
        success: function(data){
            $('tbody').empty();
            fillGenresData();
        }
    })
}

/*---*/

/*Book*/
function fillAuthors(){
    $.get('/api/authors').done(function (data) {
        $.each(data, function(index, value){
            $('#author-input').append(`<option value=${value.id}>${value.name}</option>`);
        });
    })
}

function fillGenres(){
    $.get('/api/genres').done(function (data) {
        $.each(data, function(index, value){
            $('#genres-input').append(`<input type="checkbox" class="genres-input-checkbox" name="genres" value="${value.id}">${value.name}`)
        });
    })
}

function fillBookData(){
    $.get(url()).done(function (data) {
        $('#id-input').attr('value', data.id);
        $('#name-input').val(data.name);
        let name = '#author-input option[value=' + data.author.id + ']';
        $(name).attr('selected', true);
        $.each(data.genres, function(index, value){
            let selector2 = '.genres-input-checkbox[value=' + value.id + ']';
            $(selector2).attr("checked", true);
        });
    })
}

function callBook(){
    let obj = new Object();
    obj.id = $("#id-input").attr("value");
    obj.name = $("#name-input").val();
    obj.author = $("#author-input option:selected").val();
    let checked = [];
    $("input:checkbox:checked").each(function() {
        checked.push($(this).val());
    });
    obj.genres = checked;
    let json = JSON.stringify(obj);

    $.ajax({
        url: "/books",
        type: "POST",
        data: json,
        contentType: 'application/json',
        success: function(data, status, xhr){
            window.location.replace("/books");
        }
    })
}

/*---*/

/*Genre*/
function fillGenreData(){
        $.get(url()).done(function (data) {
            $('#id-input').attr('value', data.id);
            $('#name-input').val(data.name);
        })
}

function callGenre(){
    let obj = new Object();
    obj.id = $("#id-input").attr("value");
    obj.name = $("#name-input").val();
    let json = JSON.stringify(obj);

    $.ajax({
        url: "/genres",
        type: "POST",
        data: json,
        contentType: 'application/json',
        success: function(data, status, xhr){
            window.location.replace("/genres");
        }
    })
}

/*---*/

/*Authors*/
function fillAuthorsData(){
    $.get(url()).done(function (data) {
        data.forEach(function (object) {
            $('tbody').append(`
                <tr>
                    <td>${object.id}</td>
                    <td>${object.name}</td>
                    <td>
                        <button onclick="sendEdit('/authors/${object.id}')">Edit</button>
                    </td>
                    <td>
                        <button onclick="sendAuthorsDelete('authors/${object.id}')">Delete</button>
                    </td>
                </tr>
            `)
        });
    })
}

function sendAuthorsDelete(url) {
    $.ajax({
        url: url,
        type: 'DELETE',
        success: function(data){
            $('tbody').empty();
            fillAuthorsData();
        }
    })
}

/*---*/

/*Author*/
function fillAuthorData(){
    $.get(url()).done(function (data) {
        $('#id-input').attr('value', data.id);
        $('#name-input').val(data.name);
    });
}

function callAuthor(){
    let obj = new Object();
    obj.id = $("#id-input").attr("value");
    obj.name = $("#name-input").val();
    let json = JSON.stringify(obj);

    $.ajax({
        url: "/authors",
        type: "POST",
        data: json,
        contentType: 'application/json',
        success: function(data, status, xhr){
            window.location.replace("/authors");
        }
    })
}

/*---*/
