import React from 'react'
import { Link, Navigate, Route, BrowserRouter as Router, RouterProvider, Routes, createBrowserRouter } from 'react-router-dom'
import { useKeycloak } from '@react-keycloak/web'
import Home from '../components/Home'
import AddCategory from '../components/AddCategory'
import AddProduct from '../components/AddProduct'
import ProductItem from '../components/ProductItem'
import Navigation from '../components/Navigation'
import AuthWrapper from '../utility/AuthWrapper'
import Cart from '../components/pages/Cart'
import RootLayout from '../layout/RootLayout'
import AdminAuthWrapper from '../utility/AdminAuthWrapper'
import Categories from '../components/pages/Categories'
import Products from '../components/pages/Products'
import AllCategories from '../components/AllCategories'

const CaffeinatedRouter = () => {
  const {initialized, keycloak} = useKeycloak();
  if (!initialized) {
    // return <h3>Loading ... !!!</h3>;
   }

  const router = createBrowserRouter([
    {
      path :'/',
      element: <RootLayout/>,
      children:[
        {path:'/', element:<Home/>},
        {path:'/categories', element: <Categories style="mt-24" />},
        {path:'/categories/:categoryId/products', element:<Products/>},
        {path: 'admin',
          element: <AdminAuthWrapper/>,
          children:[
            // {path:"categories", element :<AllCategories/>},
            {path:"categories/addCategory", element: <AddCategory/>},
            {path:"products/addProduct", element: <AddProduct mode="add"/>},
            {path:"products/updateProduct/:productId", element: <AddCategory mode="update"/>}
          ]
        },
        {
          path: '/user',
          element: <AuthWrapper/>,
          children:[
            {path:"cart", element:<Cart/>}
          ]
        }
      ]
    }
    // {
    //   path :'/',
    //   element: <RootLayout/>,
    //   children:[
    //     {path:'/', element:<Home/>},
    //     {path:'/categories', element: <Categories/>},
    //     {path:'/categories/:categoryId/products', element:<Products/>},
    //     {path: '/admin',
    //       element: <AdminAuthWrapper/>,
    //       children:[
    //         {path:"categories/addCategory", element: <AddCategory/>},
    //         {path:"products/addProduct", element: <AddProduct mode="add"/>},
    //         {path:"products/updateProduct/:productId", element: <AddCategory mode="update"/>}
    //       ]
    //     },
    //     {
    //       path: '/user',
    //       element: <AuthWrapper/>,
    //       children:[
    //         {path:"cart", element:<Cart/>}
    //       ]
    //     }
    //   ]
    // }
    
  ])

  return <RouterProvider router={router}/>
    {/* <><Router>
    <Navigation/>
        <Routes>
            <Route path="/" element={<Home/>}/>
            <Route path="/categories" element={<Categories/>}/>
            <Route path="/categories/addCategory" element={<AddCategory/>}/>
            <Route path="/categories/:categoryId/products" element={<Products/>}/>
            <Route path="/products/:productId" element={<ProductItem/>}/>
            <Route path="/products/addProduct" element={<AddProduct mode="add"/>}/>
            <Route path="/products/updateProduct/:productId" element={<AddProduct mode="update"/>}/>
            <Route path="/cart" element={<AuthWrapper><Cart/></AuthWrapper>}/>
            <Route path="/products" element={<PrivateRoute roles={["ADMIN"]}/>}>
              <Route element={<Products/>}/>
            </Route>

            <Route path="*" element={<Navigate to="/" />} />
        </Routes>
  </Router> */}
  // </>
  
}

export default CaffeinatedRouter