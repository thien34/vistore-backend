import ProductInfo from './ProductInfo'
import ProductPrice from './ProductPrice'
import ProductShipping from './ProductShipping'
import ProductInventory from './ProductInventory'
import ProductAction from './ProductAction'
import { Col, Row } from 'antd'

export default function ProductCreate() {
    return (
        <>
            <div className='mb-5 bg-[#fff] rounded-lg shadow-md p-6 min-h-40'>
                <ProductAction isUpdate={false} />
                <Row gutter={16}>
                    <Col span={14}>
                        <ProductInfo />
                    </Col>
                    <Col span={10}>
                        <ProductPrice />
                        <ProductShipping />
                        <ProductInventory />
                    </Col>
                </Row>
            </div>
        </>
    )
}
