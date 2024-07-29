import { Button, FormProps, InputNumber, Modal, Radio, Select } from 'antd'
import { Checkbox, Form, Input } from 'antd'
import { Dispatch, SetStateAction, useEffect } from 'react'
import useProductAtbCombinationsViewModel from './ProductAtbCombinations.vm'
import { ProductAttributeCombinationRequest } from '@/model/ProductAttributeCombination'

type Props = {
    isModalOpen: boolean
    setIsModalOpen: Dispatch<SetStateAction<boolean>>
    selectedRecord: ProductAttributeCombinationRequest | null
}

export default function ProductAtbCombinationsModal({ isModalOpen, selectedRecord, setIsModalOpen }: Props) {
    const [form] = Form.useForm()

    const { data, handleCreate, onFinishFailed, initialValues } = useProductAtbCombinationsViewModel()
    const handleCancel = () => {
        setIsModalOpen(false)
        form.resetFields()
    }
    useEffect(() => {
        if (selectedRecord) {
            const attributes = JSON.parse(selectedRecord.attributesXml).attributes.reduce(
                (acc: any, attr: any) => ({ ...acc, ...attr }),
                {},
            )
            form.setFieldsValue({ ...selectedRecord, ...attributes })
        }
    }, [selectedRecord, form])
    const renderFormItems = () => {
        return data.map((item) => {
            const fieldName = item.name.toLowerCase().replace(' ', '_')
            switch (item.type) {
                case 'select':
                    initialValues[fieldName] = item.attributes[0]?.value
                    return (
                        <Form.Item
                            key={item.name}
                            label={item.name}
                            name={item.name.toLowerCase().replace(' ', '_')}
                            rules={
                                item.isRequired
                                    ? [{ required: true, message: `Please select a ${item.name.toLowerCase()}` }]
                                    : []
                            }
                        >
                            <Select>
                                {item.attributes.map((attr) => (
                                    <Select.Option key={attr.id} value={attr.value}>
                                        {attr.value}
                                    </Select.Option>
                                ))}
                            </Select>
                        </Form.Item>
                    )
                case 'radio':
                    return (
                        <Form.Item
                            key={item.name}
                            label={item.name}
                            name={item.name.toLowerCase().replace(' ', '_')}
                            rules={
                                item.isRequired
                                    ? [{ required: true, message: `Please select a ${item.name.toLowerCase()}` }]
                                    : []
                            }
                        >
                            <Radio.Group>
                                {item.attributes.map((attr) => (
                                    <Radio key={attr.id} value={attr.value}>
                                        {attr.value}
                                    </Radio>
                                ))}
                            </Radio.Group>
                        </Form.Item>
                    )
                default:
                    return null
            }
        })
    }

    const onFinish: FormProps<ProductAttributeCombinationRequest>['onFinish'] = (values) => {
        handleCreate(values)
        setIsModalOpen(false)
    }
    return (
        <>
            <Modal
                closable={true}
                width={750}
                title='Basic Modal'
                open={isModalOpen}
                onCancel={handleCancel}
                footer={null}
            >
                <Form
                    form={form}
                    name='basic'
                    labelCol={{ span: 8 }}
                    wrapperCol={{ span: 16 }}
                    style={{ maxWidth: 600 }}
                    initialValues={initialValues}
                    onFinish={onFinish}
                    onFinishFailed={onFinishFailed}
                    autoComplete='off'
                >
                    {renderFormItems()}
                    <Form.Item<ProductAttributeCombinationRequest> label='Stock quantity' name='stockQuantity'>
                        <InputNumber min={1} max={10000} className='w-[100%]' />
                    </Form.Item>

                    <Form.Item<ProductAttributeCombinationRequest> label='Minimum stock qty' name='minStockQuantity'>
                        <InputNumber min={0} className='w-[100%]' />
                    </Form.Item>

                    <Form.Item<ProductAttributeCombinationRequest>
                        label='Allow out of stock'
                        valuePropName='checked'
                        name='allowOutOfStockOrders'
                    >
                        <Checkbox></Checkbox>
                    </Form.Item>

                    <Form.Item<ProductAttributeCombinationRequest> label='SKU' name='sku'>
                        <Input />
                    </Form.Item>

                    <Form.Item<ProductAttributeCombinationRequest>
                        label='Manufacturer part number'
                        name='manufacturerPartNumber'
                    >
                        <Input />
                    </Form.Item>

                    <Form.Item<ProductAttributeCombinationRequest> label='GTIN' name='gtin'>
                        <Input />
                    </Form.Item>

                    <Form.Item<ProductAttributeCombinationRequest> label='Overridden price' name='overriddenPrice'>
                        <InputNumber min={0} className='w-[100%]' />
                    </Form.Item>

                    <Form.Item<ProductAttributeCombinationRequest> label='Pictures' name='pictureIds'>
                        <Checkbox>Checkbox</Checkbox>
                    </Form.Item>

                    <div style={{ display: 'none' }}>
                        <Form.Item<ProductAttributeCombinationRequest> label='Overridden price' name='id'>
                            <InputNumber className='w-[100%]' />
                        </Form.Item>
                    </div>

                    <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
                        <Button type='primary' htmlType='submit'>
                            Submit
                        </Button>
                    </Form.Item>
                </Form>
            </Modal>
        </>
    )
}
