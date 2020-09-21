<template>
    <div>
        <h3>Hey {{fullName}}, here is your money: {{value}}$</h3>
    </div>
</template>

<script>
    export default {
        name: 'AccountInfo',
        props: {
            fullName: String,
            value: Number
        }
    }
</script>
