import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import StockPartenaire from './stock-partenaire';
import StockPartenaireDetail from './stock-partenaire-detail';
import StockPartenaireUpdate from './stock-partenaire-update';
import StockPartenaireDeleteDialog from './stock-partenaire-delete-dialog';

const StockPartenaireRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<StockPartenaire />} />
    <Route path="new" element={<StockPartenaireUpdate />} />
    <Route path=":id">
      <Route index element={<StockPartenaireDetail />} />
      <Route path="edit" element={<StockPartenaireUpdate />} />
      <Route path="delete" element={<StockPartenaireDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StockPartenaireRoutes;
