import React, { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import { useSelector } from 'react-redux';
import { fetchCategories } from '../store/slices/categorySlice';
import { getProductById, updateProduct } from '../store/slices/productSlice';

import { useNavigate, useParams } from 'react-router-dom';
import { addProduct } from '../store/slices/productSlice';

const AddProductForm = ({ mode }) => {
  const {productId} = useParams()
  const dispatch = useDispatch();
  const navigate = useNavigate();

  useEffect(() => {
    if (mode === 'update') {
      dispatch(getProductById(productId));
    }
  }, [dispatch, mode, productId]);

  const productData = useSelector((state) => state.products.data[productId]);

  const { data: categoryData } = useSelector((state) => state.categories);
  const [formData, setFormData] = useState({
    name: '',
    description: '',
    price: 0,
    stockQuantity: 0,
    available: false,
    categoryId: 0,
    image: null,
    discountPercentage: 0,
    discountStartDate: '',
    discountEndDate: '',
  });

  useEffect(() => {
    if (categoryData.length === 0) {
      dispatch(fetchCategories());
    }
  }, [categoryData.length, dispatch]);

  useEffect(() => {
    if (mode === 'update' && productData) {
      setFormData({
        name: productData.name || '',
        description: productData.description || '',
        price: productData.price || 0,
        stockQuantity: productData.stockQuantity || 0,
        available: productData.available || false,
        categoryId: productData.categoryId || 0,
        image: null, 
        discountPercentage: productData.discountPercentage || 0,
        discountStartDate: productData.discountStartDate || '',
        discountEndDate: productData.discountEndDate || '',
      });
    }
  }, [mode, productData]);


  const handleChange = e => {
    const { name, value, type, checked, files } = e.target;

    if (type === 'checkbox') {
      setFormData(prevForm => ({
        ...prevForm,
        [name]: checked,
      }));
    } else if (type === 'file') {
      setFormData(prevForm => ({
        ...prevForm,
        [name]: files[0],
      }));
    } else {
      setFormData(prevForm => ({
        ...prevForm,
        [name]: value,
      }));
    }
  };

  const handleSubmit = e => {
    e.preventDefault();
    const productData = { ...formData, available: formData.stockQuantity > 0 };

    if (mode === 'add') {
      dispatch(addProduct(productData));
    } else if (mode === 'update') {
      console.log("Update product triggered",productData)
      dispatch(updateProduct({ productId: productId, product: productData }));
    }

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
      <h2 className="text-2xl font-semibold mb-4">{mode === 'add' ? 'Add New Product' : 'Update Product'}</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-4">
          <label className="block text-sm font-bold mb-2" htmlFor="name">
            Name
          </label>
          <input type="text" id="name" name="name" value={formData.name} onChange={handleChange} className="w-full border rounded-md p-2" required />
        </div>
        <div className="mb-4">
          <label className="block text-sm font-bold mb-2" htmlFor="description">
            Description
          </label>
          <input type="text" id="description" name="description" value={formData.description} onChange={handleChange} className="w-full border rounded-md p-2" required />
        </div>
        <div className="mb-4">
          <label className="block text-sm font-bold mb-2" htmlFor="price">
            Price
          </label>
          <input type="number" id="price" name="price" value={formData.price} onChange={handleChange} className="w-full border rounded-md p-2" required />
        </div>
        <div className="mb-4">
          <label className="block text-sm font-bold mb-2" htmlFor="stockQuantity">
            Stock Quantity:
          </label>
          <input type="number" id="stockQuantity" name="stockQuantity" value={formData.stockQuantity} onChange={handleChange} className="w-full border rounded-md p-2" required />
        </div>
        <div className="mb-4">
          <label className="block text-sm font-bold mb-2" htmlFor="categoryId">
            Category
          </label>
          <select id="categoryId" name="categoryId" value={formData.categoryId} onChange={handleChange} className="w-full border rounded-md p-2" required>
            <option value={0} disabled>
              Select a Category
            </option>
            {categoryData.map(category => (
              <option key={category.id} value={category.id}>
                {category.name}
              </option>
            ))}
          </select>
        </div>
        <div className="mb-4">
          <label className="block text-sm font-bold mb-2" htmlFor="image">
            Image
          </label>
          <input type="file" id="image" name="image" onChange={handleChange} className="w-full border rounded-md p-2" />
        </div>
        <div className="mb-4">
          <label className="block text-sm font-bold mb-2" htmlFor="discountPercentage">
            Discount Percentage
          </label>
          <input
            type="number"
            id="discountPercentage"
            name="discountPercentage"
            value={formData.discountPercentage}
            onChange={handleChange}
            className="w-full border rounded-md p-2"
          />
        </div>
        <div className="mb-4">
          <label className="block text-sm font-bold mb-2" htmlFor="discountStartDate">
            Discount Start Date
          </label>
          <input
            type="date"
            id="discountStartDate"
            name="discountStartDate"
            value={formData.discountStartDate}
            onChange={handleChange}
            className="w-full border rounded-md p-2"
          />
        </div>
        <div className="mb-4">
          <label className="block text-sm font-bold mb-2" htmlFor="discountEndDate">
            Discount End Date
          </label>
          <input
            type="date"
            id="discountEndDate"
            name="discountEndDate"
            value={formData.discountEndDate}
            onChange={handleChange}
            className="w-full border rounded-md p-2"
          />
        </div>
        <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600">
          {mode === 'add' ? 'Submit' : 'Update'}
        </button>
      </form>
      <button onClick={() => navigate(-1)}>Go Back</button>
    </div>
  );
};

export default AddProductForm;
