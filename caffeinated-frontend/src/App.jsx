import './App.css'
import Categories from './components/Categories'
import { Link, Navigate, Route, BrowserRouter as Router, Routes } from 'react-router-dom'
import Home from './components/Home'
import Products from './components/Products'
import ProductItem from './components/ProductItem'
import AddProduct from './components/AddProduct'
import AddCategory from './components/AddCategory'

function App() {
  return (
    <>
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

export default App
