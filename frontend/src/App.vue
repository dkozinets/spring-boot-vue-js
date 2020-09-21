<template>
    <div id="app">
        <AccountManaging/>
    </div>
</template>

<script>
    import AccountManaging from './components/AccountManaging.vue'

    export default {
        name: 'App',
        components: {
            AccountManaging
        }
    }
</script>

<style>
    #app {
        font-family: Avenir, Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
        margin-top: 60px;
    }
</style>
