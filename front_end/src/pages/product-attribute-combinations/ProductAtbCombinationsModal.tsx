import { Button, Card, FormProps, Image, InputNumber, Modal, Radio, Select, Typography } from 'antd'
import { Checkbox, Form, Input } from 'antd'
import { Dispatch, SetStateAction, useEffect } from 'react'
import useProductAtbCombinationsViewModel, { ProductAtbMapping } from './ProductAtbCombinations.vm'
import { ProductAttributeCombinationRequest } from '@/model/ProductAttributeCombination'
import useGetByIdApi from '@/hooks/use-get-by-id-api'
import ProductAtbCombinationsConfig from './ProductAtbCombinationsConfig'
const { Text } = Typography

type Props = {
    isModalOpen: boolean
    setIsModalOpen: Dispatch<SetStateAction<boolean>>
    selectedRecord: ProductAttributeCombinationRequest | null
}

export default function ProductAtbCombinationsModal({ isModalOpen, selectedRecord, setIsModalOpen }: Props) {
    const [form] = Form.useForm()

    const { handleCreate, onFinishFailed, initialValues, error } = useProductAtbCombinationsViewModel()
    const handleCancel = () => {
        //   form.resetFields()
        setIsModalOpen(false)
    }

    const { data } = useGetByIdApi<ProductAtbMapping[]>(
        ProductAtbCombinationsConfig.resourceUrlByProductIdMapping,
        ProductAtbCombinationsConfig.resourceUrlByProductIdMapping,
        1,
    )
    console.log(`<<<<<  data2 >>>>>`, data)
    useEffect(() => {
        if (selectedRecord) {
            const attributes = JSON.parse(selectedRecord.attributesXml).attributes.reduce(
                (acc: any, attr: any) => ({ ...acc, ...attr }),
                {},
            )
            form.setFieldsValue({ ...selectedRecord, ...attributes })
        } else {
            form.resetFields() // Ensure form is reset if no record is selected
        }
    }, [selectedRecord, form])
    const renderFormItems = () => {
        return (
            data &&
            data.map((item) => {
                const fieldName = item.attName.toLowerCase().replace(' ', '_')
                switch (item.attributeControlTypeId) {
                    case 'DROPDOWN':
                        initialValues[fieldName] = item?.productAttributeValueResponses[0]?.name
                        return (
                            <Form.Item
                                key={item.attName}
                                label={item.attName}
                                name={item.attName.toLowerCase().replace(' ', '_')}
                                rules={
                                    item.isRequired
                                        ? [{ required: true, message: `Please select a ${item.attName.toLowerCase()}` }]
                                        : []
                                }
                            >
                                {item.productAttributeValueResponses.length > 0 && (
                                    <Select>
                                        {item.productAttributeValueResponses.map((attr) => (
                                            <Select.Option key={attr.id} value={attr.name}>
                                                {attr.name}
                                            </Select.Option>
                                        ))}
                                    </Select>
                                )}
                            </Form.Item>
                        )
                    case 'RADIO_BUTTON':
                        return (
                            <Form.Item
                                key={item.attName}
                                label={item.attName}
                                name={item.attName.toLowerCase().replace(' ', '_')}
                                rules={
                                    item.isRequired
                                        ? [{ required: true, message: `Please select a ${item.attName.toLowerCase()}` }]
                                        : []
                                }
                            >
                                {item.productAttributeValueRequests.length > 0 && (
                                    <Radio.Group>
                                        {item.productAttributeValueRequests.map((attr) => (
                                            <Radio key={attr.id} value={attr.name}>
                                                {attr.name}
                                            </Radio>
                                        ))}
                                    </Radio.Group>
                                )}
                            </Form.Item>
                        )
                    default:
                        return null
                }
            })
        )
    }

    const onFinish: FormProps<ProductAttributeCombinationRequest>['onFinish'] = async (values) => {
        try {
            await handleCreate(values)

            form.resetFields()
            setIsModalOpen(false)
        } catch (error) {}
    }
    return (
        <>
            <Modal
                maskClosable={false}
                closable={true}
                width={750}
                title='Select new combination and enter details below'
                open={isModalOpen}
                onCancel={handleCancel}
                footer={null}
            >
                {error && (
                    <Card title='' className='mb-10' size='small'>
                        <Text type='danger'>{error}</Text>
                    </Card>
                )}

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
                        <div className='flex items-center -mt-2'>
                            <Checkbox></Checkbox>
                            <Image
                                className='ms-10'
                                width={50}
                                src='https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png'
                            />
                        </div>
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
