import { Collapse } from 'antd'
import ProductInfo from './ProductInfo'
import ProductPrice from './ProductPrice'
import ProductShipping from './ProductShipping'
import ProductInventory from './ProductInventory'
import ProductAction from './ProductAction'

const { Panel } = Collapse

export default function ProductCreate() {
    return (
        <>
            <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
                <ProductAction isUpdate={false} />
                <ProductInfo />
                <ProductPrice />
                <ProductShipping />
                <ProductInventory />
                <Collapse defaultActiveKey={['1']} className='mb-6'>
                    <Panel header='Product Specification Attributes' key='1'></Panel>
                </Collapse>
            </div>
        </>
    )
}
