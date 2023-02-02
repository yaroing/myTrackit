import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TypeAction from './type-action';
import TypeActionDetail from './type-action-detail';
import TypeActionUpdate from './type-action-update';
import TypeActionDeleteDialog from './type-action-delete-dialog';

const TypeActionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TypeAction />} />
    <Route path="new" element={<TypeActionUpdate />} />
    <Route path=":id">
      <Route index element={<TypeActionDetail />} />
      <Route path="edit" element={<TypeActionUpdate />} />
      <Route path="delete" element={<TypeActionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TypeActionRoutes;
