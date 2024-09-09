import { useState } from 'react'
import { Collapse, Form, Input, Checkbox } from 'antd'
import { useDispatch, useSelector } from 'react-redux'
import { RootState, AppDispatch } from '@/redux/store/productStore'
import { setProduct } from '@/slice/productSlice'
import { CheckboxChangeEvent } from 'antd/es/checkbox'

const { Panel } = Collapse
const { Item } = Form

export default function ProductInventory(): JSX.Element {
    const [isDisplayAvailability, setIsDisplayAvailability] = useState(false)
    const dispatch = useDispatch<AppDispatch>()
    const product = useSelector((state: RootState) => state.product)

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target
        dispatch(setProduct({ [name]: parseFloat(value) || 0 }))
    }

    const handleCheckboxChange = (e: CheckboxChangeEvent) => {
        const { name, checked } = e.target as { name: string; checked: boolean }
        dispatch(setProduct({ [name]: checked }))
    }

    return (
        <Collapse defaultActiveKey={['1']} className='mb-6'>
            <Panel header='Inventory' key='1'>
                <Form layout='vertical'>
                    <Item label='Display availability' valuePropName='checked'>
                        <Checkbox
                            name='displayStockAvailability'
                            checked={product.displayStockAvailability}
                            onChange={(e) => {
                                setIsDisplayAvailability(e.target.checked)
                                handleCheckboxChange(e)
                            }}
                        />
                    </Item>
                    {isDisplayAvailability && (
                        <Item label='Display stock quantity' valuePropName='checked'>
                            <Checkbox
                                name='displayStockQuantity'
                                checked={product.displayStockQuantity}
                                onChange={handleCheckboxChange}
                            />
                        </Item>
                    )}
                    <Item label='Stock quantity'>
                        <Input name='stockQuantity' value={product.minStockQuantity} onChange={handleInputChange} />
                    </Item>

                    <Item label='Minimum cart qty'>
                        <Input name='minCartQty' value={product.minCartQty} onChange={handleInputChange} />
                    </Item>
                    <Item label='Maximum cart qty'>
                        <Input name='maxCartQty' value={product.maxCartQty} onChange={handleInputChange} />
                    </Item>
                    <Item label='Not returnable' valuePropName='checked'>
                        <Checkbox
                            name='notReturnable'
                            checked={product.notReturnable}
                            onChange={handleCheckboxChange}
                        />
                    </Item>
                </Form>
            </Panel>
        </Collapse>
    )
}
