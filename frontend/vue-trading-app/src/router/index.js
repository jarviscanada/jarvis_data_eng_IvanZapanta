import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import AddTraderView from "@/views/AddTraderView.vue";
import EditTraderView from "@/views/EditTraderView.vue";
import QuoteListView from "@/views/QuoteListView.vue";
import NotFoundView from "@/views/NotFoundView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/traders/add",
      name: "add-trader",
      component: AddTraderView,
    },
    {
      path: "/traders/edit/:id",
      name: "edit-trader",
      component: EditTraderView,
    },
    {
      path: "/quotes",
      name: "quotes-list",
      component: QuoteListView,
    },
    {
      path: "/:catchAll(.*)",
      name: "not-found",
      component: NotFoundView,
    },
  ],
});

export default router;
