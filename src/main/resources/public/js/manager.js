var countBookByClientApi = Vue.resource('/library/manager/books{/id}');
var largestFineApi = Vue.resource('/library/manager/largestFine');
var fineClientApi = Vue.resource('/library/manager/fineByClient{/id}');
var threePopularBooksApi = Vue.resource('/library/manager/getThreePopularBooks');

Vue.component('count-book-client', {
    data: function () {
        return {
            client_id: '',
            result_countBookByClient: ''
        }
    },
    template:
        '<div>' +
        '<div> Количество книг у клиента:</div>' +
        '<input type="number" readonly v-model="result_countBookByClient"/>' +
        '<input type="number" placeholder="write client id" v-model="client_id"/>' +
        '<input type="button" @click="get" value="Result"/>' +
        '</div>',
    methods: {
        get: function () {
            countBookByClientApi.get({id: this.client_id}).then(result =>
                this.result_countBookByClient = result.body
            )
        }
    }
});

Vue.component('largest-fine', {
    data: function () {
        return {
            largestFine: ''
        }
    },
    template:
        '<div>' +
        '<div> Самый большой штраф:</div>' +
        '<input type="number" readonly v-model="largestFine"/>' +
        '<input type="button" @click="get" value="Result"/>' +
        '</div>',
    methods: {
        get: function () {
            largestFineApi.get().then(result =>
                this.largestFine = result.body)
        }
    }
});

Vue.component('fine-client', {
    data: function () {
        return {
            client_id: '',
            result_fineClient: ''
        }
    },
    template:
        '<div>' +
        '<div> Штраф клиента:</div>' +
        '<input type="number" readonly v-model="result_fineClient"/>' +
        '<input type="number" placeholder="write client id" v-model="client_id"/>' +
        '<input type="button" @click="get" value="Result"/>' +
        '</div>',
    methods: {
        get: function () {
            fineClientApi.get({id: this.client_id}).then(result =>
                this.result_fineClient = result.body
            )
        }
    }
});

Vue.component('book-row', {
    props: ['book'],
    template:
        '<div> ' +
        '<i>' +
        'ID: ({{ book.id }}), ' +
        '</i> ' +
        'Name: {{ book.name }}, ' +
        'Count: {{ book.count }}, ' +
        'BookType Name: {{ book.bookType.name }} .' +
        '</div>'
});

Vue.component('three-popular-books', {
    props: ['books'],
    template:
        '<div>' +
        '<p><b>Три самые популярные книги:</b>' +
        '<book-row v-for="book in books" :key="book.id" :book="book"/>' +
        '</p>' +
        '<input type="button" @click="get" value="Result"/>' +
        '</div>',
    methods: {
        get: function () {
            this.books.splice(0, this.books.length);
            threePopularBooksApi.get().then(result =>
                result.json().then(data =>
                    data.forEach(book => this.books.push(book))
                )
            )
        }
    }
});

Vue.component('manager-list', {
    props: ['books'],
    template:
        '<div>' +
        '<div>' +
        '<count-book-client/>' +
        '</div>' +
        '<div>' +
        '<largest-fine/>' +
        '</div>' +
        '<div>' +
        '<fine-client/>' +
        '</div>' +
        '<div>' +
        '<three-popular-books :books="books"/>' +
        '</div>' +
        '</div>'
});

var manager = new Vue({
    el: '#manager',
    template: '<manager-list :books="books"/>',
    data: {
        books: []
    }
});

