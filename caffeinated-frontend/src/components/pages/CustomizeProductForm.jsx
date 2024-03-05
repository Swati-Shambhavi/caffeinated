import axios from 'axios'
import React, { useState } from 'react'
import DisplayCustomizedProduct from './DisplayCustomizedProduct'
import LoadingAnimation from '../UI/LoadingAnimation '

const CustomizationProductForm = () => {
  const options = ['low', 'medium', 'high']
  const [hideForm, setHideForm] = useState(false)
  const handleOptionChange = (e) => {
    setFormData((prevFormData) => ({
      ...prevFormData,
      caffeineLevel: e.target.value,
    }))
  }
  const [formData, setFormData] = useState({
    desiredEffects: [],
    preferredFlavors: [],
    dietaryRestrictions: [],
    desiredFormat: '',
    caffeineLevel: '',
    budget: '',
    context: '',
  })
  const [customizedProductResponse, setCustomizedProductResponse] =
    useState(null)
  const [isLoading, setIsLoading] = useState(false)

  const handleChange = (e) => {
    const { name, value } = e.target

    if (
      name === 'desiredEffects' ||
      name === 'preferredFlavors' ||
      name === 'dietaryRestrictions'
    ) {
      let valuesArray = value.split(',').map((item) => item.trim())
      setFormData((prevFormData) => ({
        ...prevFormData,
        [name]: valuesArray,
      }))
    } else {
      setFormData((prevFormData) => ({
        ...prevFormData,
        [name]: value,
      }))
    }
  }
  const unhideForm = () => {
    setHideForm(false)
  }

  const handleSubmit = async (e) => {
    setIsLoading(true)
    e.preventDefault()
    console.log('reactjs request', formData)
    try {
      const response = await axios.post(
        'http://localhost:8080/caffeinated/products/api/customize',
        formData
      )
      console.log('Raw Customized Product Response', response)
      if (response.status >= 200 && response.status < 300) {
        const data = response.data
        console.log('JSON Customized Product Response', data)
        setCustomizedProductResponse(data)
        setHideForm(true)
        return data
      } else {
        throw new Error(`HTTP error! Status: ${response.status}`)
      }
    } catch (error) {
      console.error('Error:', error)
    } finally {
      setIsLoading(false)
    }
  }
  return (
    <>
      {!hideForm && (
        <form
          onSubmit={handleSubmit}
          className='shadow-md rounded-md font-sans px-6 mb-4 pb-4 bg-amber-50 bg-opacity-5 backdrop-filter backdrop-blur-md'
        >
          <div className='flex justify-center items-center w-full relative'>
            <label htmlFor='preferredFlavors' className='w-1/3'>
              Preferred flavors:
            </label>
            <input
              type='text'
              value={formData.preferredFlavors}
              placeholder='Eg: coffee, chocolate, etc.'
              onChange={handleChange}
              name='preferredFlavors'
              className='border-b border-gray-400 py-1 px-2 w-full bg-transparent focus:outline-none focus:border-black ml-4'
            />
          </div>
          <div className='flex justify-center items-center w-full relative'>
            <label htmlFor='desiredEffects' className='w-1/3'>
              Desired effects:
            </label>
            <input
              type='text'
              value={formData.desiredEffects}
              placeholder='Eg: energizing, tasty, etc.'
              onChange={handleChange}
              name='desiredEffects'
              className='border-b border-gray-400 py-1 px-2 w-full bg-transparent focus:outline-none focus:border-black ml-4'
            />
          </div>
          <div className='flex justify-center items-center w-full'>
            <label htmlFor='desiredFormat' className='w-1/3'>
              Desired format:
            </label>
            <input
              type='text'
              value={formData.desiredFormat}
              onChange={handleChange}
              name='desiredFormat'
              placeholder='Eg: bar, pie, smoothie, etc'
              className='border-b border-gray-400 py-1 px-2 w-full bg-transparent focus:outline-none focus:border-black ml-4'
            />
          </div>

          <div className='flex justify-center items-center w-full'>
            <label htmlFor='dietaryRestrictions' className='w-1/3'>
              Dietary restrictions:
            </label>
            <input
              type='text'
              value={formData.dietaryRestrictions}
              onChange={handleChange}
              name='dietaryRestrictions'
              placeholder='Eg: gluten-free, no-sugar, etc.'
              className='border-b border-gray-400 py-1 px-2 w-full bg-transparent focus:outline-none focus:border-black ml-4'
            />
          </div>

          <div className='mb-2 flex space-x-2 items-center'>
            <span className='mr-16'>Caffeine level:</span>
            <select
              value={formData.caffeineLevel}
              onChange={handleOptionChange}
              name='caffeineLevel'
              className='py-1 px-2 text-black bg-transparent border-b border-gray-400 focus:outline-none focus:border-black'
            >
              {options.map((option) => (
                <option key={option} value={option}>
                  {option.charAt(0).toUpperCase() + option.slice(1)}
                </option>
              ))}
            </select>
          </div>
          <div className='flex flex-col items-start w-full relative'>
            <label htmlFor='context' className='mb-2'>
              Extra information:
            </label>
            <input
              type='text'
              value={formData.context}
              onChange={handleChange}
              name='context'
              className='border border-gray-400 py-1 px-2 w-full bg-transparent focus:outline-none focus:border-black h-16 mb-2'
            />
          </div>

          <div className='flex justify-center'>
            <button
              type='submit'
              className={`font-bold ${
                isLoading ? `bg-gray-600 hover:cursor-wait` : `bg-black`
              } text-amber-50 px-4 py-2 rounded hover:bg-slate-900 w-2/3 text-center mb-2`}
            >
              {isLoading ? 'Submitting...' : 'Submit'}
            </button>
          </div>
          {isLoading && <LoadingAnimation />}
        </form>
      )}
      {customizedProductResponse && (
        <DisplayCustomizedProduct
          customizedProduct={customizedProductResponse}
          unhideForm={unhideForm}
          extraProductInformation={formData}
        />
      )}
    </>
  )
}

export default CustomizationProductForm
