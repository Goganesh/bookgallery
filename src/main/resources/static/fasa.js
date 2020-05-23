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
                    <td>${object.authorDto.name}</td>
                    <td id="${object.id}"></td>
                    <td>
                        <button onclick="sendEdit('/books/${object.id}')">Edit</button>
                    </td>
                    <td>
                        <button onclick="sendBooksDelete('/books/${object.id}')">Delete</button>
                    </td>
                </tr>
            `)
            $.each(object.genresDto, function(index, value){
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
            data.forEach(function (object) {
                $('tbody').append(`
                    <tr>
                        <td>${object.id}</td>
                        <td>${object.name}</td>
                        <td>${object.authorDto.name}</td>
                        <td id="${object.id}"></td>
                        <td>
                            <button onclick="sendEdit('/books/${object.id}')">Edit</button>
                        </td>
                        <td>
                            <button onclick="sendBooksDelete('/books/${object.id}')">Delete</button>
                        </td>
                    </tr>
                `)
                $.each(object.genresDto, function(index, value){
                    $('#' + object.id).append(value.name + ' ');
                });
            });
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
        let name = '#author-input option[value=' + data.authorDto.id + ']';
        $(name).attr('selected', true);
        $.each(data.genresDto, function(index, value){
            let selector2 = '.genres-input-checkbox[value=' + value.id + ']';
            $(selector2).attr("checked", true);
        });
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
/*---*/
