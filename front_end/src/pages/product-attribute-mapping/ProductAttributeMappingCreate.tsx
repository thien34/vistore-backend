import AppActions from '@/constants/AppActions '
import { Button, Checkbox, Collapse, Form, Input, InputNumber, Select, Table } from 'antd'
import useProductAttributeMappingCreateViewModel from './ProductAttributeMappingCreate.vm'
import ProductAttributeValueCreateModal from './ProductAttributeValueCreateModal'

export default function ProductAttributeMappingCreate() {
    const {
        layout,
        onFinish,
        columns,
        productAttributeName,
        initialValue,
        handleAdd,
        form,
        open,
        setOpen,
        handleOpenModal,
        dataSource,
        handleAddValue,
        handleUpdateValue,
        isAddMode,
        editingRecord,
    } = useProductAttributeMappingCreateViewModel()

    return (
        <>
            <div className='flex justify-between mb-5'>
                <div className='text-2xl font-medium '>
                    <div className=''>Add product attribute</div>
                </div>
                <div className='flex gap-4 flex-wrap'>
                    <Button className='bg-[#475569] text-white' size='large' onClick={handleAdd}>
                        {AppActions.ADD}
                    </Button>
                </div>
            </div>
            <div className='bg-[#fff] rounded-lg shadow-md p-6'>
                <Collapse
                    collapsible='header'
                    defaultActiveKey={['1']}
                    items={[
                        {
                            key: '1',
                            label: 'Info',
                            children: (
                                <Form
                                    {...layout}
                                    form={form}
                                    size='large'
                                    onFinish={onFinish}
                                    initialValues={initialValue}
                                >
                                    <Form.Item
                                        label='Attribute'
                                        name='productAttributeId'
                                        rules={[
                                            {
                                                validator: (_, value) =>
                                                    value > 0
                                                        ? Promise.resolve()
                                                        : Promise.reject('Please select the attribute!'),
                                            },
                                        ]}
                                    >
                                        <Select
                                            placeholder='[none]'
                                            options={[
                                                { label: '[None]', value: 0 },
                                                ...(productAttributeName?.map((item) => ({
                                                    label: item.name,
                                                    value: item.id,
                                                })) ?? []),
                                            ]}
                                        />
                                    </Form.Item>
                                    <Form.Item label='Text prompt' name='textPrompt'>
                                        <Input />
                                    </Form.Item>
                                    <Form.Item label='Is Required' name='isRequired' valuePropName='checked'>
                                        <Checkbox />
                                    </Form.Item>
                                    <Form.Item label='Control type' name='attributeControlTypeId'>
                                        <Select
                                            placeholder='[none]'
                                            options={[
                                                {
                                                    label: 'Dropdown list',
                                                    value: 0,
                                                },
                                                {
                                                    label: 'Radio list',
                                                    value: 1,
                                                },
                                                {
                                                    label: 'Color squares',
                                                    value: 2,
                                                },
                                            ]}
                                        ></Select>
                                    </Form.Item>
                                    <Form.Item
                                        label='Display order'
                                        name='displayOrder'
                                        rules={[{ type: 'number', message: 'Please input a valid number!' }]}
                                    >
                                        <InputNumber />
                                    </Form.Item>
                                </Form>
                            ),
                        },
                    ]}
                />
                <Collapse
                    className='mt-5'
                    collapsible='header'
                    defaultActiveKey={['2']}
                    items={[
                        {
                            key: '2',
                            label: 'Values',
                            children: (
                                <>
                                    <Table
                                        rowKey='name'
                                        bordered
                                        columns={columns}
                                        dataSource={dataSource}
                                        pagination={false}
                                        size='small'
                                        scroll={{ x: 650 }}
                                    />
                                    <div className='mt-5'>
                                        <Button
                                            className='bg-[#475569] text-white'
                                            size='large'
                                            onClick={handleOpenModal}
                                        >
                                            {AppActions.ADD}
                                        </Button>
                                        <ProductAttributeValueCreateModal
                                            open={open}
                                            setOpen={setOpen}
                                            onAddValue={handleAddValue}
                                            openUpdateValue={handleUpdateValue}
                                            isAdd={isAddMode}
                                            editingRecord={editingRecord}
                                        />
                                    </div>
                                </>
                            ),
                        },
                    ]}
                />
            </div>
        </>
    )
}
