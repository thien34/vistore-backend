import { Collapse, Form, Input, Select } from 'antd'
import { useDispatch, useSelector } from 'react-redux'
import { RootState, AppDispatch } from '@/redux/store/productStore'
import { setProduct } from '@/slice/productSlice'
import useGetApi from '@/hooks/use-get-api'
import { DiscountNameResponse } from '@/model/Discount'
import DiscountConfigs from '../discount/DiscountConfigs'

const { Panel } = Collapse

const ProductPrice = (): JSX.Element => {
    const dispatch = useDispatch<AppDispatch>()
    const product = useSelector((state: RootState) => state.product)

    const { data: discounts } = useGetApi<DiscountNameResponse[]>(
        DiscountConfigs.resourceUrlListName,
        DiscountConfigs.resourceKey,
        { discountType: 1 },
    )

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target
        dispatch(setProduct({ [name]: parseFloat(value) || 0 }))
    }

    const handleDiscountsChange = (selectedValues: number[]) => {
        dispatch(setProduct({ discountIds: selectedValues }))
    }

    return (
        <Collapse defaultActiveKey={['1']} className='mb-6'>
            <Panel header='Prices' key='1'>
                <Form layout='vertical'>
                    <Form.Item label='Price'>
                        <Input
                            name='unitPrice'
                            addonAfter='USD'
                            value={product.unitPrice}
                            onChange={handleInputChange}
                        />
                    </Form.Item>
                    <Form.Item label='Old price'>
                        <Input name='oldPrice' addonAfter='USD' value={product.oldPrice} onChange={handleInputChange} />
                    </Form.Item>
                    <Form.Item label='Product cost'>
                        <Input
                            name='productCost'
                            addonAfter='USD'
                            value={product.productCost}
                            onChange={handleInputChange}
                        />
                    </Form.Item>

                    <Form.Item label='Discount'>
                        <Select
                            mode='multiple'
                            style={{ width: '100%' }}
                            value={product.discountIds ?? []}
                            options={discounts?.map((discount) => ({
                                value: discount.id,
                                label: discount.name,
                            }))}
                            onChange={handleDiscountsChange}
                        />
                    </Form.Item>
                </Form>
            </Panel>
        </Collapse>
    )
}

export default ProductPrice
