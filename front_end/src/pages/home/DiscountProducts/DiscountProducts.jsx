import Products from '@/components/Products'
const itemProduct = {
    id: 1,
    name: 'Samsung Galaxy Watch6 Classic 47mm Black',
    image: '/Iphone 14 pro.png',
    price: 100,
    currency: 'USD',
    path: '/product/1',
    isLiked: false
}

const generateProduct = () => {
    const products = []
    for (let index = 0; index < 4; index++) {
        itemProduct.id += index
        itemProduct.price += index
        products.push({
            ...itemProduct
        })
    }
    return products
}
const data = generateProduct()
const DiscountProducts = () => {
    return (
        <div className='max-w-screen-xl mx-auto px-4 py-20'>
            <h5 className='text-2xl font-medium'>Discounts up to - 50%</h5>
            <div className='mt-8'>
                <Products products={data} />
            </div>
        </div>
    )
}

export default DiscountProducts
