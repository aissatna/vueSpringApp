<template>
  <main>
    <EmployeeList />
  </main>
</template>

<script>
import EmployeeList from './components/EmployeeList.vue'
export default {
  name: 'App',
  components: {
    EmployeeList
  }
}
</script>

<style>

#app{
  font-family: 'Lora', serif;
}


main{
  margin: 0 auto;
  padding: 30px;
  background-color: white;
 
}
footer{
   position: fixed;
   left: 0;
   height: 50px;
   bottom: 0;
   width: 100%;
   background: linear-gradient(to left,#649173 , #dbd5a4);
   text-align: center;  
    
}
</style>
