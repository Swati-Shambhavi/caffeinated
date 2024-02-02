import './App.css'
import Categories from './components/Categories'
import { Link, Navigate, Route, BrowserRouter as Router, Routes } from 'react-router-dom'
import Home from './components/Home'
import Products from './components/Products'
import ProductItem from './components/ProductItem'
import AddProductForm from './components/AddProductForm'

function App() {
  return (
    <>
    <Router>
      <Routes>
      <Route path="/" element={<Home/>}/>
      <Route path="/categories" element={<Categories/>}/>
      <Route path="/categories/:categoryId/products" element={<Products/>}/>
      <Route path="/products/:productId" element={<ProductItem/>}/>
      <Route path="/products/addProduct" element={<AddProductForm/>}/>

      <Route path="*" element={<Navigate to="/" />} />
      </Routes>
    </Router>

    </>
  )
}

export default App
