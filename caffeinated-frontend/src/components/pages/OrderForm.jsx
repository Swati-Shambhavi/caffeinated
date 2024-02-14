import React from 'react'
import Cart from './Cart'
import { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { updateCartUser } from '../../store/slices/cartSlice';
import { useNavigate } from 'react-router-dom';

const OrderForm = ({user, setShowOrderForm}) => {
  const dispatch = useDispatch()
  const navigate = useNavigate()
  const [formData, setFormData] = useState({
    name: user.name || '',
    mobileNumber: user.mobileNumber || '',
    address1: user.address ? user.address.address1 || '' : '',
    address2: user.address ? user.address.address2 || '' : '',
    city: user.address ? user.address.city || '' : '',
    state: user.address ? user.address.state || '' : '',
    pincode: user.address ? user.address.pincode || '' : '',
  });

  useEffect(() => {
    setFormData({
      name: user.name || '',
      mobileNumber: user.mobileNumber || '',
      address1: user.address ? user.address.address1 || '' : '',
      address2: user.address ? user.address.address2 || '' : '',
      city: user.address ? user.address.city || '' : '',
      state: user.address ? user.address.state || '' : '',
      pincode: user.address ? user.address.pinCode || '' : '',
    });
  }, [user]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log('Form submitted:', formData);
    await dispatch(updateCartUser(formData))
    setShowOrderForm(false)
    navigate("/user/cart")
  };

  return (
    <form className="max-w-md mx-auto mt-8 p-4 bg-amber-800 bg-opacity-10 backdrop-filter backdrop-blur-2xl rounded">
      <label className="block mb-2">
        Name:
        <input
          type="text"
          name="name"
          value={formData.name}
          onChange={handleChange}
          className="w-full border-b rounded px-3 py-2 mt-1"
        />
      </label>
      <label className="block mb-2">
        Mobile Number:
        <input
          type="text"
          name="mobileNumber"
          value={formData.mobileNumber}
          onChange={handleChange}
          className="w-full border-b rounded px-3 py-2 mt-1"
        />
      </label>
      <label className="block mb-2">
        Address 1:
        <input
          type="text"
          name="address1"
          value={formData.address1}
          onChange={handleChange}
          className="w-full border-b rounded px-3 py-2 mt-1"
        />
      </label>
      <label className="block mb-2">
        Address 2:
        <input
          type="text"
          name="address2"
          value={formData.address2}
          onChange={handleChange}
          className="w-full border-b rounded px-3 py-2 mt-1"
        />
      </label>
      <label className="block mb-2">
        City:
        <input
          type="text"
          name="city"
          value={formData.city}
          onChange={handleChange}
          className="w-full border-b rounded px-3 py-2 mt-1"
        />
      </label>
      <label className="block mb-2">
        State:
        <input
          type="text"
          name="state"
          value={formData.state}
          onChange={handleChange}
          className="w-full border-b rounded px-3 py-2 mt-1"
        />
      </label>
      <label className="block mb-2">
        Pincode:
        <input
          type="text"
          name="pincode"
          value={formData.pincode}
          onChange={handleChange}
          className="w-full border-b rounded px-3 py-2 mt-1"
        />
      </label>
      <div className='flex justify-center'>
      <button
        type="submit"
        className="bg-black text-amber-50 px-4 py-2 rounded hover:bg-slate-900 " 
        onClick={handleSubmit}
      >
        Submit
      </button>
      <button onClick={()=>setShowOrderForm(false)} className='bg-black text-amber-50 px-4 py-2 rounded hover:bg-slate-900 ml-4'>Go back</button>
   
      </div>
      </form>
  );
};

export default OrderForm;
