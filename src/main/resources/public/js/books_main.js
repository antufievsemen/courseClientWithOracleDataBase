var booksApi = Vue.resource('/library/books{/id}');

function getIndex(list, id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

Vue.component('book-form', {
    props: ['books', 'bookAttr'],
    data: function () {
        return {
            id: '',
            book_name: '',
            book_count: '',
            book_bookTypeId: ''
        }
    },
    watch: {
        bookAttr: function (newVal, oldVal) {
            this.id = newVal.id;
            this.book_name = newVal.name;
            this.book_count = newVal.count;
            this.book_bookTypeId = newVal.bookType.id;
        }
    },
    template:
        '<div>' +
        '<p><b>Название книги:</b>' +
        '<input type="text" placeholder="Write name" v-model="book_name"/>' +
        '</p>' +
        '<p><b>Количество книг:</b>' +
        '<input type="number" placeholder="Write count" v-model="book_count"/>' +
        '</p>' +
        '<p><b>Индентификатор типа книги:</b>' +
        '<input type="number" placeholder="Write bookType id" v-model="book_bookTypeId"/>' +
        '</p>' +
        '<input type="button" value="Save" @click="save"/>' +
        '</div>',
    methods: {
        save: function () {
            var book = {
                name: this.book_name,
                count: this.book_count,
                bookType: {
                    id: this.book_bookTypeId
                }
            }
            if (this.id) {
                booksApi.update({id: this.id}, book).then(result =>
                    result.json().then(data => {
                            var index = getIndex(this.books, data.id);
                            this.books.splice(index, 1, data);
                            this.id = '';
                            this.book_name = '';
                            this.book_count = '';
                            this.book_bookTypeId = '';
                        }
                    )
                )
            } else {
                booksApi.save({}, book).then(result =>
                    result.json().then(data => {
                        this.books.push(data);
                        this.name = '';
                        this.count = '';
                        this.bookType = '';
                    })
                )
            }
        }
    }
});

Vue.component('book-row', {
    props: ['book', 'editBook', 'books'],
    data: function () {
        return {
            bookType_notNull: true
        }
    },
    template:
        '<div> ' +
        '<i>' +
        'ID: ({{ book.id }}), ' +
        '</i> ' +
        'Name: {{ book.name }}, ' +
        'Count: {{ book.count }}, ' +
        '<div v-if="bookType_notNull"> ' +
        'BookType Name: {{ book.bookType.name }} .' +
        '</div>' +
        '<span style="position: absolute; right: 0;">' +
        '<input type="button" value="Edit" @click="edit" />' +
        '<input type="button" value="Delete" @click="del" />' +
        '</span>' +
        '</div>',
    created: function () {
      if (this.book.bookType == null) {
          this.bookType_notNull = false;
      }
    },
    methods: {
        edit: function () {
            this.editBook(this.book);
        },
        del: function () {
            booksApi.remove({id: this.book.id}).then(result => {
                if (result.ok) {
                    this.books.splice(this.books.indexOf(this.book), 1);
                }
            })
        }
    }
});

Vue.component('books-list', {
    props: ['books'],
    data: function () {
        return {
            book: null
        }
    },
    template:
        '<div>' +
        '<book-form :books="books" :bookAttr="book"/>' +
        '<book-row v-for="book in books" :key="book.id" :book="book" :editBook="editBook" :books="books"/>' +
        '</div>',
    created: function () {
        booksApi.get().then(result =>
            result.json().then(data =>
                data.forEach(book => this.books.push(book))
            )
        )
    },
    methods: {
        editBook: function (book) {
            this.book = book;
        }
    }
});


var bookList = new Vue({
    el: '#books_main',
    template: '<books-list :books="books"/>',
    data: {
        books: []
    }
});