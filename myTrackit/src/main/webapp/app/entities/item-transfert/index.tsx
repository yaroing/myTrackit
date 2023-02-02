import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ItemTransfert from './item-transfert';
import ItemTransfertDetail from './item-transfert-detail';
import ItemTransfertUpdate from './item-transfert-update';
import ItemTransfertDeleteDialog from './item-transfert-delete-dialog';

const ItemTransfertRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ItemTransfert />} />
    <Route path="new" element={<ItemTransfertUpdate />} />
    <Route path=":id">
      <Route index element={<ItemTransfertDetail />} />
      <Route path="edit" element={<ItemTransfertUpdate />} />
      <Route path="delete" element={<ItemTransfertDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ItemTransfertRoutes;
