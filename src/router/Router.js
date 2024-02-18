const routes = [
  {
    path: "/register",
    component: () => import("../views/register/_index.vue"),
  },
  {
    path: "/login",
    component: () => import("../views/login/_index.vue"),
  },
  {
    path: "/post",
    component: () => import("../views/createPost/_index.vue"),
  },
  {
    path: "/post-details/:id",
    component: () => import("../views/postDetails/_index.vue"),
  },
  {
    path: "/post-list",
    name: "PostDetails",
    component: () => import("../views/listPost/_index.vue"),
  },
];
export default routes;
