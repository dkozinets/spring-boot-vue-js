<template>
    <div class="hello">
        <AccountInfo v-if="!isFetching"
                     :value=userResponse.value
                     :full-name=userResponse.fullName />
        <div>
            <b-form @submit="onSubmit">
                <b-form-group
                        id="input-group-1"
                        label="Email address:"
                        label-for="input-1"
                        description="We'll never share your email with anyone else.">
                    <b-form-input
                            id="input-1"
                            v-model="form.amount"
                            type="number"
                            required
                            placeholder="Enter amount"
                    ></b-form-input>
                </b-form-group>
                <b-form-group id="input-group-3" label="Type:" label-for="input-3">
                    <b-form-select
                            id="input-3"
                            v-model="form.type"
                            :options="types"
                            required
                    ></b-form-select>
                </b-form-group>
                <b-button type="submit" variant="primary">Submit</b-button>
            </b-form>
        </div>
        <div>
            <b-button @click="getAllTransactions" block variant="primary">Get all my transactions</b-button>
        </div>
        <div v-if="transactions">
            <b-table striped hover :items="transactions"></b-table>
        </div>
        <div v-if="error">{{error}}</div>
    </div>
</template>

<script>
    import api from "./api";
    import AccountInfo from './AccountInfo.vue'

    export default {
        name: 'AccountManaging',
        components: {
            AccountInfo
        },

        data() {
            return {
                form: {
                    amount: null,
                    type: null,
                },
                transactions: null,
                isFetching: true,
                userResponse: null,
                test: 0,
                error: null,
                selected: 1,
                options: [
                    {value: 1, text: 'DEBIT'},
                    {value: 2, text: 'CREDIT'},
                ],
                types: [{text: 'Select One', value: null}, 'DEBIT', 'CREDIT']
            }
        },

        beforeMount() {
            this.getUser()
        },

        methods: {
            onSubmit(evt) {
                this.error = null
                evt.preventDefault()
                api.commitTransaction(this.form).then(() => {
                    this.getUser()
                }).catch(exception => this.error = exception)
            },

            getUser() {
                this.error = null
                api.getUser().then(response => {
                    this.userResponse = response.data;
                    //It looks like a hack.
                    this.$set(this.userResponse, 'value', response.data.value)
                    console.log(response.data)
                    this.isFetching = false;
                }).catch(exception => this.error = exception)
            },

            getAllTransactions() {
                this.transactions = null
                api.getAllTransactions().then(response => {
                    this.transactions = response.data
                }).catch(exception => this.error = exception)
            }
        }
    }
</script>