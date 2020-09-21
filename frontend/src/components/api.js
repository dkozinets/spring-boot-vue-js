import axios from 'axios'

const AXIOS = axios.create({
    baseURL: `http://localhost:8080/api`,
    timeout: 1000
});


export default {
    getAllTransactions() {
        return AXIOS.get(`/transactions`);
    },
    commitTransaction(transactionBody) {
        return AXIOS.post(`/transactions`, transactionBody)
    },
    getUser() {
        return AXIOS.get(`/users`);
    }
}
