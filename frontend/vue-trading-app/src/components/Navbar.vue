<script setup>
import { RouterLink, useRoute } from "vue-router";
import logo from "@/assets/img/logo.png";

const isActiveLink = (routePath) => {
  const route = useRoute();
  return route.path === routePath;
};
</script>

<template>
  <nav
    class="bg-red-700 w-25 p-4 flex flex-col border-r border-red-500 fixed top-0 left-0 h-full"
  >
    <!-- Logo -->
    <RouterLink class="flex items-center mb-8" to="/">
      <img class="h-10 w-auto" :src="logo" alt="Vue Jobs" />
    </RouterLink>

    <!-- Navigation links -->
    <div class="flex flex-col space-y-4 mt-4 items-center">
      <RouterLink
        to="/"
        :class="[
          isActiveLink('/')
            ? 'bg-red-900'
            : 'hover:bg-gray-900 hover:text-white',
          'text-white',
          'rounded-full',
          'px-3',
          'py-2',
        ]"
        ><i class="pi pi-user"></i
      ></RouterLink>
      <RouterLink
        to="/quotes"
        :class="[
          isActiveLink('/quotes')
            ? 'bg-red-900'
            : 'hover:bg-gray-900 hover:text-white',
          'text-white',
          'rounded-full',
          'px-3',
          'py-2',
        ]"
        ><i class="pi pi-chart-line"></i
      ></RouterLink>
    </div>
  </nav>
</template>
