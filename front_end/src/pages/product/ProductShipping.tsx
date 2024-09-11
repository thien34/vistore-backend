import { Collapse, Form, Input, Checkbox } from 'antd'
import { useSelector } from 'react-redux'
import { RootState } from '@/redux/store/productStore'
import useProductCreateViewModel from './ProductCreate.vm'

const { Panel } = Collapse
const { Item } = Form

export default function ProductShipping(): JSX.Element {
    const product = useSelector((state: RootState) => state.product)
    const { handleInputChange, handleCheckboxChange } = useProductCreateViewModel()

    return (
        <Collapse defaultActiveKey={['1']} className='mb-6'>
            <Panel header='Shipping' key='1'>
                <Form layout='vertical'>
                    <Item label='Shipping enabled' valuePropName='checked'>
                        <Checkbox
                            name='isShipEnabled'
                            checked={product.isShipEnabled}
                            onChange={handleCheckboxChange}
                        />
                    </Item>
                    {product.isShipEnabled && (
                        <>
                            <Item label='Weight'>
                                <Input
                                    name='weight'
                                    addonAfter='kg(s)'
                                    value={product.weight}
                                    onChange={handleInputChange}
                                />
                            </Item>
                            <Item label='Length'>
                                <Input
                                    name='length'
                                    addonAfter='meter(s)'
                                    value={product.length}
                                    onChange={handleInputChange}
                                />
                            </Item>
                            <Item label='Width'>
                                <Input
                                    name='width'
                                    addonAfter='meter(s)'
                                    value={product.width}
                                    onChange={handleInputChange}
                                />
                            </Item>
                            <Item label='Height'>
                                <Input
                                    name='height'
                                    addonAfter='meter(s)'
                                    value={product.height}
                                    onChange={handleInputChange}
                                />
                            </Item>
                            <Item label='Free shipping' valuePropName='checked'>
                                <Checkbox
                                    name='isFreeShipping'
                                    checked={product.isFreeShipping}
                                    onChange={handleCheckboxChange}
                                />
                            </Item>
                        </>
                    )}
                </Form>
            </Panel>
        </Collapse>
    )
}
