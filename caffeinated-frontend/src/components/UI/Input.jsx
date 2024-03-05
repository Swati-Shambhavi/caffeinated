const Input = ({ formData, handleChange, inputDetail }) => {
  return (
    <div className='flex justify-center items-center w-full relative'>
      <label htmlFor={inputDetail.name} className='w-1/3'>
        {inputDetail.description}
      </label>
      <input
        type='text'
        value={formData[inputDetail.name]}
        placeholder={inputDetail.placeholder}
        onChange={handleChange}
        name={inputDetail.name}
        className='border-b border-gray-300 p-2 w-full bg-transparent focus:outline-none focus:border-black ml-4'
      />
    </div>
  )
}

export default Input
