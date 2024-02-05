import React from 'react'
import { Link, Navigate, Route, BrowserRouter as Router, Routes } from 'react-router-dom'
import { useState } from 'react'
import { useKeycloak } from '@react-keycloak/web'
import Home from '../components/Home'
import Categories from '../components/Categories'
import AddCategory from '../components/AddCategory'
import AddProduct from '../components/AddProduct'
import Products from '../components/Products'
import ProductItem from '../components/ProductItem'
import Navigation from '../components/Navigation'

const CaffeinatedRouter = () => {
    const {initialized} = useKeycloak();
    if (!initialized) {
        return <h3>Loading ... !!!</h3>;
    }
  return (<>
  <Navigation/>
    <Router>
        <Routes>
            <Route path="/" element={<Home/>}/>
            <Route path="/categories" element={<Categories/>}/>
            <Route path="/categories/addCategory" element={<AddCategory/>}/>
            <Route path="/categories/:categoryId/products" element={<Products/>}/>
            <Route path="/products" element={<Products/>}/>
            <Route path="/products/:productId" element={<ProductItem/>}/>
            <Route path="/products/addProduct" element={<AddProduct mode="add"/>}/>
            <Route path="/products/updateProduct/:productId" element={<AddProduct mode="update"/>}/>

            <Route path="*" element={<Navigate to="/" />} />
        </Routes>
  </Router>
  </>
  )
}

export default CaffeinatedRouter