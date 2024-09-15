import { Form, Input, Button, Modal, Table, Checkbox, InputNumber } from 'antd'
import { PlusOutlined, SaveOutlined } from '@ant-design/icons'
import useProductAttributeCreate from '@/pages/product-attributes/ProductAttributeCreate.vm.ts'
import TextArea from 'antd/es/input/TextArea'
import ProductAttributeConfigs from './ProductAttributeConfigs'

const pageSize = 6

export default function ProductAttributeCreate() {
    const {
        handlePageChange,
        handleFinish,
        handleAddValue,
        isModalOpen,
        handleCancelModal,
        setIsModalOpen,
        values,
        newValue,
        columns,
        current,
        form,
        formAdd,
        setNewValue,
        layout,
    } = useProductAttributeCreate()

    return (
        <>
            <div className='flex justify-between mb-5'>
                <div className='text-2xl font-medium '>
                    <div className=''>{ProductAttributeConfigs.saveTitle}</div>
                </div>
                <div className='flex gap-4 flex-wrap'>
                    <Button className='default-btn-color' size='large' icon={<SaveOutlined />} onClick={form.submit}>
                        {ProductAttributeConfigs.saveBtn}
                    </Button>
                </div>
            </div>
            <div className='bg-[#fff] rounded-lg shadow-md p-6'>
                <Form {...layout} id='myForm' form={form} onFinish={handleFinish} layout='horizontal' size='large'>
                    <Form.Item
                        name='name'
                        label='Name'
                        rules={[{ required: true, message: 'Please enter the attribute name' }]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item name='description' label='Description'>
                        <TextArea rows={3} />
                    </Form.Item>
                </Form>
                <div className='flex justify-between mb-5'>
                    <div className='text-base'>
                        <div className=''>{ProductAttributeConfigs.predefinedTitle}</div>
                    </div>
                    <div className='flex gap-4 flex-wrap'>
                        <Button
                            icon={<PlusOutlined />}
                            className='default-btn-color'
                            size='middle'
                            onClick={() => setIsModalOpen(true)}
                        >
                            {ProductAttributeConfigs.addPredefinedBtn}
                        </Button>
                    </div>
                </div>
                <Table
                    dataSource={values}
                    columns={columns}
                    bordered
                    pagination={{
                        current,
                        pageSize,
                        total: values.length,
                        onChange: (page) => handlePageChange(page),
                    }}
                    rowKey='id'
                    scroll={{ x: 750 }}
                />

                <Modal
                    title='Add Predefined Value'
                    open={isModalOpen}
                    centered
                    onOk={formAdd.submit}
                    onCancel={handleCancelModal}
                >
                    <Form form={formAdd} layout='horizontal' size='large' onFinish={handleAddValue}>
                        <Form.Item
                            name='name'
                            label='Name'
                            rules={[{ required: true, message: 'Please enter the name' }]}
                        >
                            <Input
                                value={newValue.name}
                                onChange={(e) => setNewValue({ ...newValue, name: e.target.value })}
                            />
                        </Form.Item>
                        <Form.Item name='priceAdjustment' label='Price Adjustment'>
                            <Input
                                addonAfter={newValue.priceAdjustmentUsePercentage ? '%' : '$'}
                                value={newValue.priceAdjustment || 0}
                                onChange={(e) =>
                                    setNewValue({
                                        ...newValue,
                                        priceAdjustment: e.target.value ? parseFloat(e.target.value) : 0,
                                    })
                                }
                                type='number'
                            />
                        </Form.Item>
                        <Form.Item label='Price Adjustment Use Percentage'>
                            <Checkbox
                                checked={newValue.priceAdjustmentUsePercentage}
                                onChange={(e) =>
                                    setNewValue({
                                        ...newValue,
                                        priceAdjustmentUsePercentage: e.target.checked,
                                    })
                                }
                            />
                        </Form.Item>
                        <Form.Item name='weightAdjustment' label='Weight Adjustment'>
                            <Input
                                value={newValue.weightAdjustment || 0}
                                onChange={(e) =>
                                    setNewValue({
                                        ...newValue,
                                        weightAdjustment: e.target.value ? parseFloat(e.target.value) : 0,
                                    })
                                }
                                type='number'
                            />
                        </Form.Item>
                        <Form.Item name='cost' label='Cost'>
                            <Input
                                addonAfter={'$'}
                                value={newValue.cost || 0}
                                onChange={(e) =>
                                    setNewValue({
                                        ...newValue,
                                        cost: e.target.value ? parseFloat(e.target.value) : 0,
                                    })
                                }
                                type='number'
                            />
                        </Form.Item>
                        <Form.Item label='Is Pre-selected'>
                            <Checkbox
                                checked={newValue.isPreSelected}
                                onChange={(e) =>
                                    setNewValue({
                                        ...newValue,
                                        isPreSelected: e.target.checked,
                                    })
                                }
                            />
                        </Form.Item>
                        <Form.Item name='displayOrder' label='Display Order'>
                            <InputNumber
                                value={newValue.displayOrder || 0}
                                onChange={(value) =>
                                    setNewValue({
                                        ...newValue,
                                        displayOrder: value ?? 0,
                                    })
                                }
                            />
                        </Form.Item>
                    </Form>
                </Modal>
            </div>
        </>
    )
}
