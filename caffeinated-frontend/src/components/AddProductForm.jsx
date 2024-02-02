import React, { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
// import { addProduct } from '../path-to-your-redux-slice';
import { useSelector } from 'react-redux'; 
import { fetchCategories } from '../store/slices/categorySlice';
import { useNavigate } from 'react-router-dom';
import { addProduct } from '../store/slices/productSlice';

const AddProductForm = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate()
  const {data: categoryData} = useSelector(state => state.categories)
  const [formData, setFormData] = useState({
    name: '',
    description: '',
    price: 0,
    stockQuantity: 0,
    available: false,
    categoryId: 0,
    image: null,
    discountPercentage: 0,
    discountStartDate: null,
    discountEndDate: null,
  });

  useEffect(()=>{
    if(categoryData.length == 0){
      dispatch(fetchCategories())
    }
  },[])

  const handleChange = (e) => {
    const { name, value, type, checked, files } = e.target;

    // Handle checkbox (for available field) separately
    if (type === 'checkbox') {
      setFormData((prevForm) => ({
        ...prevForm,
        [name]: checked,
      }));
    } else if (type === 'file') {
      // Handle file input separately
      setFormData((prevForm) => ({
        ...prevForm,
        [name]: files[0], // Assuming you only want to handle the first file
      }));
    } else {
      setFormData((prevForm) => ({
        ...prevForm,
        [name]: value,
      }));
    }
  };
  

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Product Form Data:",{...formData, available:formData.stockQuantity>0})
    dispatch(addProduct({...formData, available:formData.stockQuantity>0}));
    setFormData({
      name: '',
      description: '',
      price: 0,
      stockQuantity: 0,
      available: false,
      categoryId: 0,
      image: null,
      discountPercentage: 0,
      discountStartDate: null,
      discountEndDate: null,
    });
  };

  return (
    <div className="max-w-md mx-auto mt-8 p-6 rounded-md shadow-md">
      <h2 className="text-2xl font-semibold mb-4">Add New Product</h2>
      <p>Entered Name: {formData.name}</p>
      <form onSubmit={handleSubmit}>      
        <div className="mb-4">
          <label className="block text-sm font-bold mb-2" htmlFor="name">
            Name
          </label>
          <input type="text" id="name" name="name" value={formData.name} onChange={handleChange} className="w-full border rounded-md p-2" required/>
        </div>
        <div className="mb-4">
          <label className="block text-sm font-bold mb-2" htmlFor="description">
            Description
          </label>
          <input type="text" id="description" name="description" value={formData.description} onChange={handleChange} className="w-full border rounded-md p-2" required/>
        </div>
        <div className="mb-4">
          <label className="block text-sm font-bold mb-2" htmlFor="price">
          Price
          </label>
          <input type="number" id="price" name="price" value={formData.price} onChange={handleChange} className="w-full border rounded-md p-2" required/>
        </div>
        <div className="mb-4">
          <label className="block text-sm font-bold mb-2" htmlFor="stockQuantity">
          Stock Quantity:
          </label>
          <input type="number" id="stockQuantity" name="stockQuantity" value={formData.stockQuantity} onChange={handleChange} className="w-full border rounded-md p-2" required/>
        </div>
        <div className="mb-4">
          <label className="block text-sm font-bold mb-2" htmlFor="categoryId">
            Category
          </label>
          <select
            id="categoryId" name="categoryId" value={formData.categoryId} onChange={handleChange} className="w-full border rounded-md p-2" required>
            <option value={0} disabled>Select a Category</option>
            {categoryData.map((category) => (
              <option key={category.id} value={category.id}>{category.name}</option>))}
          </select>
        </div>
  
        <div className="mb-4">
          <label className="block00 text-sm font-bold mb-2" htmlFor="image"> Image</label>
          <input type="file"id="image" name="image"onChange={handleChange} className="w-full border rounded-md p-2"/> 
        </div>
        <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600">Product
        </button>
      </form>
      <button onClick={()=>navigate(-1)}>Go Back</button>
    </div>
  );
};

export default AddProductForm;
