import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ItemVerifie from './item-verifie';
import ItemVerifieDetail from './item-verifie-detail';
import ItemVerifieUpdate from './item-verifie-update';
import ItemVerifieDeleteDialog from './item-verifie-delete-dialog';

const ItemVerifieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ItemVerifie />} />
    <Route path="new" element={<ItemVerifieUpdate />} />
    <Route path=":id">
      <Route index element={<ItemVerifieDetail />} />
      <Route path="edit" element={<ItemVerifieUpdate />} />
      <Route path="delete" element={<ItemVerifieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ItemVerifieRoutes;
