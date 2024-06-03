import Columns from '@/layouts/client/components/columns'
import Banner from './Banner'

const itemProduct = {
    id: 1,
    name: 'Ipad Pro',
    description:
        'iPad combines a magnificent 10.2-inch Retina display, incredible performance, multitasking and ease of use.',
    image: '/ipad.png',
    path: '/product/1'
}

const generateProduct = () => {
    const products = []
    for (let index = 0; index < 4; index++) {
        products.push({
            ...itemProduct
        })
    }
    return products
}
const data = generateProduct()

const bgs = ['FFF', 'F9F9F9', 'EAEAEA', '2C2C2C']

const Banners = () => {
    return (
        <Columns className='!gap-0'>
            <>
                {data.map((item, index) => (
                    <div key={index} className='col-span-3'>
                        <Banner bgColor={bgs[index]} product={item} />
                    </div>
                ))}
            </>
        </Columns>
    )
}

export default Banners
